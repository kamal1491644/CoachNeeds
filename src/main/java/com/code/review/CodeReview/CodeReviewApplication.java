package com.code.review.CodeReview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CodeReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeReviewApplication.class, args);
	}

}
