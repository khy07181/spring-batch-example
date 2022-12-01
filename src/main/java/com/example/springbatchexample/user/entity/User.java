package com.example.springbatchexample.user.entity;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_user", schema = "seavantage")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id
    private Long id;

    private String name;

    private int age;

    private LocalDateTime time;

    @Builder
    public User(Long id, String name, int age, LocalDateTime time) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.time = time;
    }

    private User duplicate() {
        return new User(id, name, age, time);
    }

    public User updateTime() throws InterruptedException {
        Thread.sleep(1000);
        User user = duplicate();
        user.time = LocalDateTime.now();
        return user;
    }

}
