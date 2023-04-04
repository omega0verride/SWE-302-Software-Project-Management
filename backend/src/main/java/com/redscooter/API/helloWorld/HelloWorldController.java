package com.redscooter.API.helloWorld;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class HelloWorldController {

    @GetMapping("/public/helloworld")
    public ResponseEntity<String> getUsers() {
        return ResponseEntity.ok().body("Hello World");
    }


}

