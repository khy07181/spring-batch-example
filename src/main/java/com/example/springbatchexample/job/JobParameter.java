package com.example.springbatchexample.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class JobParameter {

    private final LocalDateTime startDateTime;

    public JobParameter(String startDateTime) {
        this.startDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
