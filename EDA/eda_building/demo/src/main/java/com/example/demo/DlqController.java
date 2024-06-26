package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
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

    private final JobExplorer jobExplorer;
    private final JobLauncher jobLauncher;
    private final Job dlqJob;
    private final DlqService dlqService;

    @GetMapping("/dlq/batch")
    public String dlqBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobExecution jobExecution = jobLauncher.run(dlqJob, new JobParametersBuilder(jobExplorer).getNextJobParameters(dlqJob).toJobParameters());
        return jobExecution.toString();
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
