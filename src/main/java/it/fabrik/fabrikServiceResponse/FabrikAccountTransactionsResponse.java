package it.fabrik.fabrikServiceResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FabrikAccountTransactionsResponse extends FabrikBaseResponse {
    private FabrikAccountTransactionsPayloadResponse payload;
}
