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
public class LegalPersonBeneficiary {
    private String fiscalCode;
    private String legalRepresentativeFiscalCode;
}
