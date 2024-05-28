package id.ten.authapi.records;

import jakarta.validation.constraints.NotEmpty;

public record LoginRecord(
        @NotEmpty(message = "email cannot be empty")
        String email,
        @NotEmpty(message = "password cannot be empty")
        String password) {
}
