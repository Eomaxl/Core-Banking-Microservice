package com.eomaxl.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold successfull response information"
)
public class ResponseDto {
    @Schema(description = "Status code in the response", example = "200")
    private String statusCode;
    @Schema(description = "Status message in the response", example = "Request processing")
    private String statusMsg;
}
