package fullstack.api.controller;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OAuth2LoginController {

  @GetMapping("/loginSuccess")
  public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
    // Process user information and customize your logic
    log.info(
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(",")));
    return ResponseEntity.ok("Hello World!");
  }
}
