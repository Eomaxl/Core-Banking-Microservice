package com.eomaxl.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name="Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(
            description = "Account number of the customer", example = "1023055011"
    )
    @NotEmpty(message="Account Number cannot be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})", message="Account Number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of the customer", example = "SAVINGS"
    )
    @NotEmpty(message="AccountType cannot be a null or empty")
    private String accountType;

    @Schema(
            description = "Account's branch address", example = "Bellandur"
    )
    @NotEmpty(message="BranchAddress cannot be a null or empty")
    private String branchAddress;
}
