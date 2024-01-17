package petstore.pet;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
@Log4j2
@RequiredArgsConstructor
public class HealthController {
  @GetMapping
  public ResponseEntity<Object> health(@RequestHeader String host) {
    return ResponseEntity.ok().body("Hello Loan stupid! From " + host);
  }
}
