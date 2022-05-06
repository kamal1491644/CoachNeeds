package com.coach.needs.CoachNeeds.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientTypeDto {
    @JsonProperty("natural")
    private Integer natural;

    @JsonProperty("steroids")
    private Integer steroids;
}
