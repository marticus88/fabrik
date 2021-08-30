package it.fabrik.fabrikServiceResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FabrikError {
    private String code;
    private String description;
    private String params;
}
