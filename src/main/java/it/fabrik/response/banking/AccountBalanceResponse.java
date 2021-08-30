package it.fabrik.response.banking;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.fabrik.response.BaseResponse;
import it.fabrik.valueobject.Balance;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountBalanceResponse extends BaseResponse {
    private Balance balance;
}
