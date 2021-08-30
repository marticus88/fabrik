package it.fabrik.controller;

import it.fabrik.annotation.Auth;
import it.fabrik.error.ErrorCode;
import it.fabrik.error.ErrorMessage;
import it.fabrik.error.ErrorResponse;
import it.fabrik.exception.JwtException;
import it.fabrik.exception.banking.AccountBalanceException;
import it.fabrik.exception.banking.AccountTransactionsException;
import it.fabrik.exception.banking.CreateMoneyTransferException;
import it.fabrik.request.banking.CreateMoneyTransferRequest;
import it.fabrik.response.banking.AccountBalanceResponse;
import it.fabrik.response.banking.CreateMoneyTransferResponse;
import it.fabrik.service.BankingService;
import it.fabrik.service.JwtService;
import it.fabrik.valueobject.Balance;
import it.fabrik.valueobject.CreateMoneyTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/banking/")
public class BankingController {


    private final BankingService bankingService;
    private final JwtService jwtService;

    public BankingController(JwtService jwtService, BankingService bankingService) {
        this.jwtService = jwtService;
        this.bankingService = bankingService;
    }

    @Auth
    @RequestMapping(value = "/accountBalance", method = RequestMethod.GET)
    public ResponseEntity accountBalance(@RequestHeader(name = "Authorization") String bearer, @RequestHeader(name = "Request-Id") UUID requestId) {

        String accountId;
        try {
            accountId = jwtService.getAccountIdFromToken(bearer);
        } catch (JwtException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.JWT_ERROR.getCode())
                    .message(ErrorMessage.JWT_ERROR.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        Balance balance;
        try {
            balance = bankingService.accountBalance(accountId);
        } catch (AccountBalanceException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.ACCOUNT_BALANCE_ERROR.getCode())
                    .message(ErrorMessage.ACCOUNT_BALANCE_ERROR.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        AccountBalanceResponse accountBalanceResponse = AccountBalanceResponse.builder()
                .requestId(requestId)
                .balance(balance)
                .build();
        return new ResponseEntity<>(accountBalanceResponse, HttpStatus.OK);

    }

    @Auth
    @RequestMapping(value = "/createMoneyTransfer", method = RequestMethod.POST)
    public ResponseEntity createMoneyTransfer(@RequestBody CreateMoneyTransferRequest createMoneyTransferRequest, @RequestHeader(name = "Authorization") String bearer, @RequestHeader(name = "Request-Id") UUID requestId) {

        String accountId;
        try {
            accountId = jwtService.getAccountIdFromToken(bearer);
        } catch (JwtException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.JWT_ERROR.getCode())
                    .message(ErrorMessage.JWT_ERROR.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        CreateMoneyTransfer createMoneyTransfer;
        try {
            createMoneyTransfer = bankingService.createMoneyTransfer(accountId, createMoneyTransferRequest);
        } catch (CreateMoneyTransferException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.MONEY_TRANSFER_ERROR.getCode())
                    .message(ErrorMessage.MONEY_TRANSFER_ERROR.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        CreateMoneyTransferResponse createMoneyTransferResponse = CreateMoneyTransferResponse.builder()
                .requestId(requestId)
                .createMoneyTransfer(createMoneyTransfer)
                .build();
        return new ResponseEntity<>(createMoneyTransferResponse, HttpStatus.OK);
    }

    @Auth
    @RequestMapping(value = "/accountTransactions", method = RequestMethod.POST)
    public ResponseEntity accountTransactions(@RequestParam String fromAccountingDate, @RequestParam String toAccountingDate, @RequestHeader(name = "Authorization") String bearer, @RequestHeader(name = "Request-Id") UUID requestId) {


        String accountId;
        try {
            accountId = jwtService.getAccountIdFromToken(bearer);
        } catch (JwtException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.JWT_ERROR.getCode())
                    .message(ErrorMessage.JWT_ERROR.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        CreateMoneyTransfer accountTrannsaction;
        try {
            accountTrannsaction = bankingService.accountTransactions(accountId, fromAccountingDate, toAccountingDate);
        } catch (AccountTransactionsException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.ACCOUNT_TANSTACTIONS_ERROR.getCode())
                    .message(ErrorMessage.ACCOUNT_TANSTACTIONS_ERROR.getMessage())
                    .status("FAILURE")
                    .requestId(requestId)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        CreateMoneyTransferResponse createMoneyTransferResponse = CreateMoneyTransferResponse.builder()
                .requestId(requestId)
                .createMoneyTransfer(accountTrannsaction)
                .build();
        return new ResponseEntity<>(createMoneyTransferResponse, HttpStatus.OK);

    }

}
