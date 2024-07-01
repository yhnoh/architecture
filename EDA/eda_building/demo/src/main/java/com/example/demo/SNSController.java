package com.example.demo;

import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SNSController {

    private final SnsTemplate snsTemplate;

    @GetMapping("/sns/send")
    public void sendSNS() {

        snsTemplate.convertAndSend("HELLO_SNS", "hello");
    }
}
