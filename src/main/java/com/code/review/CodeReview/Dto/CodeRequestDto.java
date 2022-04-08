package com.code.review.CodeReview.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CodeRequestDto {
    @JsonProperty("code")
    private String code;
    @JsonProperty("programming_languages")
    private String programmingLanguages;
}
