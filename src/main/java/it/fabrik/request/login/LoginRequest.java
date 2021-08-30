package it.fabrik.request.login;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class LoginRequest {

    @NotBlank(message = "AccountId is mandatory")
    private String accountId;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
