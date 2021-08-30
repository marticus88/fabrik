package it.fabrik.controller;

import it.fabrik.error.ErrorCode;
import it.fabrik.error.ErrorMessage;
import it.fabrik.error.ErrorResponse;
import it.fabrik.exception.LoginException;
import it.fabrik.request.login.LoginRequest;
import it.fabrik.response.BaseResponse;
import it.fabrik.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/account/")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@Valid @RequestBody LoginRequest request, @RequestHeader(name = "Request-Id") UUID requestId) {

        String jwt;
        try {
            jwt = loginService.login(request.getAccountId(), request.getPassword());
        } catch (LoginException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.LOGIN.getCode())
                    .message(ErrorMessage.LOGIN.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        BaseResponse response = BaseResponse.builder()
                .jwt(jwt)
                .status("SUCCESS")
                .requestId(requestId)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
