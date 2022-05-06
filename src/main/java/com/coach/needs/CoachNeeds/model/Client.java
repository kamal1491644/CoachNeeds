package com.coach.needs.CoachNeeds.model;

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
@Table(name = "clients")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="coach_id",nullable = false)
    private Coach coach;

    @Column(name ="first_name")
    private String firstName;

    @Column(name ="last_name")
    private String lastName;

    @Column(name ="email")
    private String email;

    @Column(name ="gender")
    private String gender;

    @Column(name ="date_of_birth")
    private String dateOfBirth;

    @Column(name ="address")
    private String address;

    @Column(name ="weight")
    private String weight;

    @Column(name ="height")
    private String height;

    @Column(name ="age")
    private String age;

    @Column(name ="mobile")
    private String mobile;

    @Column(name ="whats_app")
    private String whatsApp;

    @Column(name ="paid_money")
    private Double paidMoney;

    @Column(name ="training_type")
    private String trainingType;

    @Column(name ="client_state")
    private String clientState;

    @Column(name ="country")
    private String country;

    @Column(name ="created_at")
    private Instant createdAt;
}
