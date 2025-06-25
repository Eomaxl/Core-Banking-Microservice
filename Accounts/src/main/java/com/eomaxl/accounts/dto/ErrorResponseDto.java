package com.eomaxl.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name ="Error",
        description = "Schema to persist all the error occured"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path where the error has occured"
    )
    private String apiPath;
    @Schema(
            description = "Error code of the error that has been occured"
    )
    private HttpStatus errorCode;
    @Schema (
            description = "Error message that has been occured"
    )
    private String errorMessage;
    @Schema(
            description = "Time when the error has occured"
    )
    private LocalDateTime errorTime;

}
