package it.fabrik.error;

public enum ErrorCode {

    BANK_USER_ALREADY_PRESENT("E_200"),
    BANK_USER_ACCOUNT_ID_ALREADY_PRESENT("E_201"),
    MONEY_TRANSFER_ERROR("E_202"),
    ACCOUNT_TANSTACTIONS_ERROR("E_203"),
    LOGIN("E_100"),
    JWT_ERROR("E_403"),
    ACCOUNT_BALANCE_ERROR("E_500"),
    SESSION_EXPIRED("E_401");

    private String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
