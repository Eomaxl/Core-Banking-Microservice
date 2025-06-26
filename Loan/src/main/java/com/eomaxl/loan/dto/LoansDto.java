package com.eomaxl.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema
public class LoansDto {

    @NotEmpty(message = "Please enter the 10 digit mobile number.")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Mobile number of the customer", example = "1234567890"
    )
    private String mobileNumber;

    @NotEmpty(message = "Loan number cannot be a null or empty")
    @Schema(description = "Loan number of the customer", example = "ABCS123456")
    private String loanNumber;

    @NotEmpty(message = "Loan Type cannot be null or empty")
    @Schema(description = "Type of the loan", example = "HOME LOAN")
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(description = "Total loan amount", example = "100_000")
    private int totalLoan;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(description = "Total outstanding amount against the loan", example = "1000")
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(description = "Total outstanding amount against the loan", example = "99000")
    private int outstandingAmount;
}
