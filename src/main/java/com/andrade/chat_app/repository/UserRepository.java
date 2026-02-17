package com.andrade.chat_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.andrade.chat_app.domain.User;
import com.andrade.chat_app.enums.Status;



public interface  UserRepository   extends MongoRepository<User,String>{
    Optional<List<User>> findAllByStatus(Status status);
    Optional<User> findByNickNameAndFullName(String nickName, String fullName);
}
