package com.example.springbatchexample.user.repository;

import com.example.springbatchexample.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
