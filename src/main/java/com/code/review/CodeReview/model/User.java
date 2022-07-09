package com.code.review.CodeReview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "subject_id")
    private String subjectId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;
    @Column(name = "date_of_birth")
    private Instant dateOfBirth;
    @Column(name = "country")
    private String country;
    @Column(name="city")
    private String city;
    @Column(name="area")
    private String area;
    @Column(name="mobile_number")
    private String mobileNUmber;
    @Column(name="whats_app")
    private String whatsApp;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name="age")
    private String age;




}
