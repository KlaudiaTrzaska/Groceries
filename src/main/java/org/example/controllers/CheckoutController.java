package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.services.DiscountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Checkout", description = "Checkout and discount management endpoints")
public class CheckoutController {

    DiscountService discountService;

    public CheckoutController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping(value = "/discounts/{productName}")
    @Operation(summary = "Get discount by product name", description = "Retrieves the discount information for a specific product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount information retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public String getDiscountByProductName(
            @Parameter(description = "The name of the product to check for discounts", required = true)
            @PathVariable String productName) {

        return discountService.checkIfProductHasDiscount(productName);
    }

}
