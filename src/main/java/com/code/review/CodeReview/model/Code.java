package com.code.review.CodeReview.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "code")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    private String code;
    private String programmingLanguages;
    private Instant createdAt;

}
