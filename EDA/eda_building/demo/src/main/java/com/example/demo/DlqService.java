package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesResponse;
import software.amazon.awssdk.services.sqs.model.ListMessageMoveTasksResponse;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.StartMessageMoveTaskResponse;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class DlqService {

    private final SqsAsyncClient sqsAsyncClient;

    public StartMessageMoveTaskResponse startMessageMoveTaskResponse(String queueName) throws ExecutionException, InterruptedException {
        String arn = getArn(queueName);
        return sqsAsyncClient.startMessageMoveTask(to -> to.sourceArn(arn)).get();
    }

    public ListMessageMoveTasksResponse listMessageMoveTasksResponse(String queueName) throws ExecutionException, InterruptedException {
        String arn = getArn(queueName);
        ListMessageMoveTasksResponse listMessageMoveTasksResponse = sqsAsyncClient.listMessageMoveTasks(to -> to.sourceArn(arn)).get();
        return listMessageMoveTasksResponse;
    }


    private String getArn(String queueName) throws InterruptedException, ExecutionException {

        GetQueueAttributesResponse getQueueAttributesResponse = sqsAsyncClient.getQueueUrl(to -> to.queueName(queueName))
                .thenCompose(getQueueUrlResponse -> sqsAsyncClient.getQueueAttributes(to -> to.queueUrl(getQueueUrlResponse.queueUrl())
                        .attributeNames(QueueAttributeName.QUEUE_ARN))).get();
        return getQueueAttributesResponse.attributes().get(QueueAttributeName.QUEUE_ARN);
    }

}
