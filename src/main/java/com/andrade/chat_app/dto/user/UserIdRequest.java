package com.andrade.chat_app.dto.user;

import jakarta.validation.constraints.NotBlank;

public record  UserIdRequest (@NotBlank String id){
    
}
