package com.code.review.CodeReview.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class RegisterUserDto {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("date_of_birth")
    private Instant dateOfBirth;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("area")
    private String area;

    @JsonProperty("mobile_number")
    private String mobileNUmber;

    @JsonProperty("whats_app")
    private String whatsApp;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("age")
    private String age;


}
