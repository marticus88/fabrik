package it.fabrik.exception.banking;

public class AccountBalanceException extends Exception {
    public AccountBalanceException(String message) {
        super(message);
    }
}
