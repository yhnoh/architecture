package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.model.ListMessageMoveTasksResponse;
import software.amazon.awssdk.services.sqs.model.ListMessageMoveTasksResultEntry;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class DlqController {

    private static final String QUEUE_NAME = "HELLO_DLQ";

    private final JobLauncher jobLauncher;
    private final Job dlqJob;
    private final DlqService dlqService;

    @GetMapping("/dlq")
    public void dlqBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(dlqJob, new JobParametersBuilder().toJobParameters());
    }


    @GetMapping("/dlq/startMessageMoveTask")
    public void startMessageMoveTask() throws ExecutionException, InterruptedException {
        dlqService.startMessageMoveTaskResponse(QUEUE_NAME);
    }


    @GetMapping("/dlq/listMessageMoveTasks")
    public String listMessageMoveTasks() throws ExecutionException, InterruptedException {
        ListMessageMoveTasksResponse listMessageMoveTasksResponse = dlqService.listMessageMoveTasksResponse(QUEUE_NAME);
        List<ListMessageMoveTasksResultEntry> results = listMessageMoveTasksResponse.results();
        return results.toString();
    }

}
