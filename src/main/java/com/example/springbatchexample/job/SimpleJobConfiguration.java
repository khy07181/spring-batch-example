package com.example.springbatchexample.job;

import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(startStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Start!");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

//    @Bean
//    @JobScope
//    Step simpleStep1() {
//        return stepBuilderFactory.get("simpleStep1")
//                .<User, User>chunk(1)
//                .reader(userJpaPagingItemReader())
//                .processor((ItemProcessor<User, User>) User::updateTime)
//                .writer(userJpaItemWriter())
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    JpaPagingItemReader<User> userJpaPagingItemReader() {
//        return new JpaPagingItemReaderBuilder<User>()
//                .name("userJpaPagingItemReader")
//                .entityManagerFactory(entityManagerFactory)
//                .pageSize(1)
//                .queryString("SELECT u FROM User u ORDER BY u.id")
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    JpaItemWriter<User> userJpaItemWriter() {
//        return new JpaItemWriterBuilder<User>().entityManagerFactory(entityManagerFactory).build();
//    }
}
