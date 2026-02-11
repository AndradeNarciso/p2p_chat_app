package com.andrade.chat_app.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.andrade.chat_app.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Document
public class User {
    @Id
    private String id;
    private String nickName;
    private String fullName;
    private Status status;

    public User() {
        this.id = UUID.randomUUID().toString();
        status=Status.ONLINE;
    }

}
