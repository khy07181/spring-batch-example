package com.example.springbatchexample.job;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobParameter jobParameter;

    @Bean
    @StepScope
    public JobParameter jobParameter(@Value("#{jobParameters[startDateTime]}") String startDateTime) {
        return new JobParameter(startDateTime);
    }

    @Bean
    Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(startStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>SimpleJob Start!");
                    log.info(jobParameter.getStartDateTime().toString());
                    TimeUnit.MINUTES.sleep(30);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
