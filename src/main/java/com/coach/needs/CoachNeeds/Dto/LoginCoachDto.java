package com.coach.needs.CoachNeeds.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginCoachDto {
    @JsonProperty("username")
    String username;
    @JsonProperty("password")
    String password;
}
