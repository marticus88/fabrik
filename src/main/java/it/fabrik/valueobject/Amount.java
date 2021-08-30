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
public class Amount {

    private BigDecimal debtorAmount;
    private BigDecimal creditorAmount;
    private String debtorCurrency;
    private String creditorCurrency;
    private String creditorCurrencyDate;
    private int exchangeRate;
}
