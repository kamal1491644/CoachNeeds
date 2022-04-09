package com.code.review.CodeReview.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String subjectId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String role;
    private Instant createdAt;
}
