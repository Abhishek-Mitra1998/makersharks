package com.makersharks.identity.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    @Column(name = "USERNAME")
    private String username;

    @JsonProperty("password")
    @Column(name = "PASSWORD")
    private String password;

    @JsonProperty("email")
    @Column(name = "EMAIL")
    private String email;

    @JsonProperty("firstName")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @JsonProperty("middleName")
    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @JsonProperty("lastName")
    @Column(name = "LAST_NAME")
    private String lastName;

    @JsonProperty("dateCreated")
    @Column(name = "DATE_CREATED")
    private String dateCreated;
}
