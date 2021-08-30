package it.fabrik.response.banking;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.fabrik.response.BaseResponse;
import it.fabrik.valueobject.CreateMoneyTransfer;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateMoneyTransferResponse extends BaseResponse {
    private CreateMoneyTransfer createMoneyTransfer;
}
