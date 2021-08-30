package it.fabrik.request.banking;

import it.fabrik.valueobject.Creditor;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CreateMoneyTransferRequest {

    private Creditor creditor;
    private String executionDate;
    private String uri;
    private String description;
    private BigDecimal amount;
    private String currency;
    private boolean isUrgent;
    private boolean isInstant;
    private String feeType;
    private String feeAccountId;


}
