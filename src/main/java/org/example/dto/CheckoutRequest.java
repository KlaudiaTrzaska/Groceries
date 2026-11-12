package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for checkout")
public class CheckoutRequest {

    @Schema(description = "Products in the basket", example = "[\"bread\", \"butter\", \"butter\"]")
    private ArrayList<String> basket = new ArrayList<>();

    @Schema(description = "Optional loyalty client phone number", example = "+1234567890")
    private String phoneNumber;
}
