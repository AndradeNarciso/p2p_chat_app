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
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String nickName;

    private String fullName;

    @Builder.Default
    private Status status = Status.ONLINE;

}
