package com.coach.needs.CoachNeeds.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("address")
    private String address;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("height")
    private String height;

    @JsonProperty("age")
    private String age;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("whats_app")
    private String whatsApp;

    @JsonProperty("paid_money")
    private Double paidMoney;

    @JsonProperty("training_type")
    private String trainingType;

    @JsonProperty("client_state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("created_at")
    private Instant createdAt;

}
