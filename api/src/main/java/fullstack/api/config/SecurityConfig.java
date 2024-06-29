package fullstack.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] WHITE_LIST_URL = {
    "/swagger-resources",
    "/swagger-resources/**",
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/auth/*/**",
    "/oauth2/*/**",
    "/login"
  };

  private final Environment environment;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  public SecurityConfig(
      Environment environment,
      JwtAuthenticationFilter jwtAuthenticationFilter,
      CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
    this.environment = environment;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(
            authorize ->
                authorize.requestMatchers(WHITE_LIST_URL).permitAll().anyRequest().authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.oauth2Login(
        oauth2 ->
            oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService()))
                .successHandler(customAuthenticationSuccessHandler));

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    http.formLogin(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
    // Here you can extract GitHub user info and create/update user in your system
    // Create or update user, generate JWT, etc.
    return new DefaultOAuth2UserService();
  }

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(
        githubClientRegistration(), googleClientRegistration());
  }

  private ClientRegistration githubClientRegistration() {
    return ClientRegistration.withRegistrationId("github")
        .clientId(
            environment.getProperty("spring.security.oauth2.client.registration.github.clientId"))
        .clientSecret(
            environment.getProperty(
                "spring.security.oauth2.client.registration.github.clientSecret"))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri("{baseUrl}/login/oauth2/code/github")
        .scope("user:email", "read:user")
        .authorizationUri("https://github.com/login/oauth/authorize")
        .tokenUri("https://github.com/login/oauth/access_token")
        .userInfoUri("https://api.github.com/user")
        .userNameAttributeName("id")
        .clientName("GitHub")
        .build();
  }

  private ClientRegistration googleClientRegistration() {
    return ClientRegistration.withRegistrationId("google")
        .clientId(
            environment.getProperty("spring.security.oauth2.client.registration.google.client-id"))
        .clientSecret(
            environment.getProperty(
                "spring.security.oauth2.client.registration.google.client-secret"))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
        .scope("openid", "profile", "email")
        .authorizationUri("https://accounts.google.com/o/oauth2/auth")
        .tokenUri("https://www.googleapis.com/oauth2/token")
        .userInfoUri("https://www.googleapis.com/oauth2/userinfo")
        .userNameAttributeName(IdTokenClaimNames.SUB)
        .jwkSetUri("https://www.googleapis.com/oauth2/v1/certs")
        .clientName("Google")
        .build();
  }
}
