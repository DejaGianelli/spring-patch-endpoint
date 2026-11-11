package com.example.demo;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/examples")
public class ExampleController {

    private final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @PatchMapping
    public ResponseEntity<Void> path(@Valid @RequestBody ExampleRequest request) {
        if (request.isInformed("foo")) {
            logger.info("foo was informed: {}", request.getFoo());
        }
        if (request.isInformed("bar")) {
            logger.info("bar was informed: {}", request.getBar());
        }
        return ResponseEntity.ok().build();
    }
}
