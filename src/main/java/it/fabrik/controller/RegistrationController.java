package it.fabrik.controller;

import it.fabrik.error.ErrorCode;
import it.fabrik.error.ErrorMessage;
import it.fabrik.error.ErrorResponse;
import it.fabrik.exception.bankUser.BankUserAccountIdException;
import it.fabrik.exception.bankUser.BankUserAlreadyPresentException;
import it.fabrik.request.registration.SignupRequest;
import it.fabrik.response.BaseResponse;
import it.fabrik.service.BankUserService;
import it.fabrik.valueobject.BankUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/account-manager/")
public class RegistrationController {

    private final BankUserService bankUserService;
    private final ModelMapper modelMapper;


    public RegistrationController(BankUserService bankUserService) {
        this.bankUserService = bankUserService;
        this.modelMapper = new ModelMapper();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity signup(@Valid @RequestBody SignupRequest request, @RequestHeader(name = "Request-Id") UUID requestId) {

        BankUser bankUser = BankUser.builder()
                .accountId(request.getAccountId())
                .password(request.getPassword())
                .build();

        try {
            bankUserService.singup(bankUser);
        } catch (BankUserAlreadyPresentException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.BANK_USER_ALREADY_PRESENT.getCode())
                    .message(ErrorMessage.BANK_USER_ALREADY_PRESENT.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (BankUserAccountIdException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.BANK_USER_ACCOUNT_ID_ALREADY_PRESENT.getCode())
                    .message(ErrorMessage.BANK_USER_ACCOUNT_ID_ALREADY_PRESENT.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        BaseResponse response = BaseResponse.builder()
                .status("SUCCESS")
                .requestId(requestId)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
