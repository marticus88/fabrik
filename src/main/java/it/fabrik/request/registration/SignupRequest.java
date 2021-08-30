package it.fabrik.request.registration;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class SignupRequest {

    @NotBlank(message = "Account is mandatory")
    private String accountId;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
