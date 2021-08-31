package it.fabrik.response.banking;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.fabrik.response.BaseResponse;
import it.fabrik.valueobject.Transaction;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTransactionsResponse extends BaseResponse {
    private List<Transaction> transactions;
}
