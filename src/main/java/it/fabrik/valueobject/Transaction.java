package it.fabrik.valueobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"searchDate", "accountId"})
public class Transaction {
    private String transactionId;
    private String accountId;
    private String operationId;
    private String accountingDate;
    private String valueDate;
    private Type type;
    private Date searchDate;
}
