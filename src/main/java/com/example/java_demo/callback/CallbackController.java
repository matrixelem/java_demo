package com.example.java_demo.callback;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/call")
public class CallbackController {

    @PostMapping("/t1")
    public void t1(@RequestBody String body) {
        System.out.println("t1============" + body);
    }
}
