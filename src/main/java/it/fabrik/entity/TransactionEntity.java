package it.fabrik.entity;

import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_seq", allocationSize = 1)
    private Integer id;

    private String accountId;
    private String transactionId;
    private String operationId;
    private Date accountingDate;
    private Date valueDate;
    private String typeEnumerration;
    private String typevalue;
}
