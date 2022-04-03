package com.code.review.CodeReview.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    @JsonProperty("user_name")
    String username;
    @JsonProperty("password")
    String password;
    @JsonProperty("email")
    String email;
    @JsonProperty("first_name")
    String firstname;
    @JsonProperty("last_name")
    String lastname;
}
