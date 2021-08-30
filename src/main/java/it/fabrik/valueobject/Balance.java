package it.fabrik.valueobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Balance {
    private String date;
    private String currency;
    private BigDecimal balance;
    private BigDecimal availableBalance;

}
