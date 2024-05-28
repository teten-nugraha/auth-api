package id.ten.authapi.records;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRecord(
        @NotEmpty(message = "email cannot be empty")
        String email,
        @NotEmpty(message = "password cannot be empty")
        String password,
        @NotEmpty(message = "fullname cannot be empty")
        String fullName) {

}
