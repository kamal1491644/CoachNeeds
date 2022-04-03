package com.code.review.CodeReview.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    String username;
    String password;
    String email;
    String firstname;
    String lastname;
}
