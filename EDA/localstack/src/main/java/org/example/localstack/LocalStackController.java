package org.example.localstack;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LocalStackController {

    private final SnsTemplate snsTemplate;

    @GetMapping("hello-world")
    public void helloWorld() {

        MessageBody messageBody = new MessageBody();
        messageBody.setMessage("helloWorld");
        snsTemplate.convertAndSend("my-topic", messageBody);
    }

    @Getter
    @Setter
    @ToString
    public static class MessageBody {
        private String message = "hello world";
    }

    @SqsListener("my-queue")
    public void receiveMessage(@Headers Map<String, String> headers, @Payload MessageBody messageBody) {
        System.out.println("headers = " + headers + ", messageBody = " + messageBody);
    }
}
