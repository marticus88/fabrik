package it.fabrik.fabrikServiceResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class FabrikCreateMoneyTransferResponse extends FabrikBaseResponse {

    private CreateMoneyTransfer payload;


}
