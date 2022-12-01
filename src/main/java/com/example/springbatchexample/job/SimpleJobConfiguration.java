package com.example.springbatchexample.job;

import com.example.springbatchexample.user.entity.User;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
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
                .start(simpleStep1())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .<User, User>chunk(1)
                .reader(userJpaPagingItemReader())
                .processor((ItemProcessor<User, User>) User::updateTime)
                .writer(userJpaItemWriter())
                .build();
    }

    @Bean
    @StepScope
    JpaPagingItemReader<User> userJpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<User>()
                .name("userJpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(1)
                .queryString("SELECT u FROM User u ORDER BY u.id")
                .build();
    }

    @Bean
    @StepScope
    JpaItemWriter<User> userJpaItemWriter() {
        return new JpaItemWriterBuilder<User>().entityManagerFactory(entityManagerFactory).build();
    }
}
