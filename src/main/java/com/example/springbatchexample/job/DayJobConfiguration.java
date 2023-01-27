package com.example.springbatchexample.job;

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
public class DayJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobParameter jobParameter;

    @Bean
    @StepScope
    public JobParameter dayJobParameter(@Value("#{jobParameters[startDateTime]}") String startDateTime) {
        return new JobParameter(startDateTime);
    }

    @Bean
    Job dayJob() {
        return jobBuilderFactory.get("dayJob")
                .start(dayStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step dayStep() {
        return stepBuilderFactory.get("dayStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Day Job start!");
                    log.info(jobParameter.getStartDateTime().toString());
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
