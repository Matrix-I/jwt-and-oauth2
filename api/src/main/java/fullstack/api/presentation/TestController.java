package fullstack.api.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Hello This is a test!");
  }

  @PreAuthorize("hasAuthority('ROLE_USER')")
  @GetMapping("/demo")
  public ResponseEntity<String> demo() {
    return ResponseEntity.ok("Hello World!");
  }
}
