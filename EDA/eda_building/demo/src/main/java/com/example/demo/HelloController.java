package com.example.demo;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final SqsTemplate sqsTemplate;
    private static final String QUEUE_NAME = "HELLO";

    @GetMapping("/hello-world")
    public String helloWorld() {
        SendResult<String> result = sqsTemplate.send(to -> to.queue(QUEUE_NAME).payload("hello world"));
        return result.message().getPayload();
    }

    @SqsListener(QUEUE_NAME)
    public void receiveMessage(@Headers Map<String, String> headers, @Payload String payload) {
        System.out.println("메시지 수신 성공!!!");
        throw new IllegalArgumentException("비지니스 로직 문제 발생");
    }

}
