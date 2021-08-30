package it.fabrik.error;

import it.fabrik.response.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class ErrorResponse extends BaseResponse {

    private String type;
    private String message;
}
