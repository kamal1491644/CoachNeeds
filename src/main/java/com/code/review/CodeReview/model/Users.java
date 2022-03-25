package com.code.review.CodeReview.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "users",schema = "CodeReview")
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "username")
    private String userName;
    @Column(name="password")
    private String password;
}
