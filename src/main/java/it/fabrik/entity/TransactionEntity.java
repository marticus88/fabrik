package it.fabrik.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "TRANSACTION")
public class TransactionEntity implements Serializable {

    @Id
    private String transactionId;
    private String accountId;
    private String operationId;
    private String accountingDate;
    private String valueDate;
    private String typeEnumeration;
    private String typeValue;
    private Date searchDate;
}
