package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DLQJobConfig {

    private final DlqService dlqService;
    private static final String JOB_NAME = "dlq";
    private static final String QUEUE_NAME = "HELLO_DLQ";

    @Bean(name = JOB_NAME + "Job")
    public Job dlqJob(JobRepository jobRepository) {
        return new JobBuilder(JOB_NAME + "Job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(this.dlqStep(null, null))
                .build();
    }

    @Bean(name = JOB_NAME + "Step")
    public Step dlqStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(JOB_NAME + "Step", jobRepository)
                .tasklet(this.dlqTasklet(), transactionManager)
                .build();
    }

    @Bean(name = JOB_NAME + "tasklet")
    public Tasklet dlqTasklet() {
        return (contribution, chunkContext) -> {
            dlqService.startMessageMoveTaskResponse(QUEUE_NAME);
            return RepeatStatus.FINISHED;
        };
    }


//    @Bean(name = JOB_NAME + "ItemReader")
//    public ItemReader<ListMessageMoveTasksResponse> dlqItemReader() {
//        return () -> dlqService.listMessageMoveTasksResponse(QUEUE_NAME);
//    }
//
//    @Bean(name = JOB_NAME + "ItemWriter")
//    public ItemWriter<ListMessageMoveTasksResponse> dlqItemWriter() {
//        return chunk -> {
//            dlqService.startMessageMoveTaskResponse(QUEUE_NAME);
//        };
//    }
}
