package com.moon.demo01.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    @EqualsAndHashCode.Exclude
    private String id;

    @Field("email")
    @NotBlank
    @Email
    private String email;

    @ToString.Exclude
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @NotBlank
    private String name;
}
/*
{
  "_id": "ObjectId",
  "email": "example@example.com",
  "password": "hashed_password",
  "name": "John sina"
}
*/