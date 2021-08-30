package it.fabrik.valueobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaturalPersonBeneficiary {
    private String fiscalCode1;
    private String fiscalCode2;
    private String fiscalCode3;
    private String fiscalCode4;
    private String fiscalCode5;
}
