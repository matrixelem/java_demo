package com.example.java_demo.multi_thread.threadLocal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tl")
@Slf4j
public class TestController {

    @RequestMapping("/t1")
    public String t1() {
        log.info("controller thread:{}", Thread.currentThread().getId());
        List<Long> ids = new ArrayList<>();
        ids.add(12L);
        TestThreadLocal.set(ids);
        return "sdsdsd";
    }


    @RequestMapping("/t2")
    public String t2() {
        log.info("t2 controller thread:{}", Thread.currentThread().getId());
        List<Long> ids = new ArrayList<>();
        ids.add(8977L);
        TestThreadLocal.set(ids);
        return "sdsdsd";
    }
}
