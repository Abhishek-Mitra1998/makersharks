package com.makersharks.identity.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {

    @NotNull
    @JsonProperty("username")
    private String username;

    @NotNull
    @JsonProperty("password")
    private String password;

    @NotNull
    @JsonProperty("confirmPassword")
    private String confirmPassword;

    @NotNull
    @JsonProperty("email")
    private String email;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("middleName")
    private String middleName;

    @JsonProperty("lastName")
    private String lastName;

}
