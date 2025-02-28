```java
package com.example.servicea;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class Application {

    private final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/call-service-b")
    @CircuitBreaker(name = "serviceB", fallbackMethod = "fallback")
    public String callServiceB() {
        return restTemplate.getForObject("http://service-b:8081/hello", String.class);
    }

    public String fallback(Exception e) {
        return "Service B is unavailable - fallback response.";
    }
}
```
