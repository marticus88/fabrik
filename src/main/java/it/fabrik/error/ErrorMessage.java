package it.fabrik.error;


public enum ErrorMessage {

    BANK_USER_ALREADY_PRESENT("Bank user already present"),
    BANK_USER_ACCOUNT_ID_ALREADY_PRESENT("AccountId already present"),
    LOGIN("Login error"),
    JWT_ERROR("Jwt error"),
    ACCOUNT_BALANCE_ERROR("Fabrik account balance fail"),
    MONEY_TRANSFER_ERROR("Fabrik money trasfer fail"),
    ACCOUNT_TANSTACTIONS_ERROR("Fabrik account transaction fail"),
    GENERIC_ERROR("Generic error"),
    SESSION_EXPIRED("Session expired");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
