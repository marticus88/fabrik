package it.fabrik.exception.bankUser;

public class BankUserAlreadyPresentException extends Exception {
    public BankUserAlreadyPresentException(String message) {
        super(message);
    }
}
