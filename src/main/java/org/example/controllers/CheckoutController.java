package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.Receipt;
import org.example.dto.CheckoutRequest;
import org.example.services.CheckoutService;
import org.example.services.DiscountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Tag(name = "Checkout", description = "Checkout and discount management endpoints")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final DiscountService discountService;

    public CheckoutController(CheckoutService checkoutService, DiscountService discountService) {
        this.checkoutService = checkoutService;
        this.discountService = discountService;
    }

    @PostMapping("/checkout")
    @Operation(summary = "Checkout a basket", description = "Calculates the receipt for a basket and adds loyalty points when a known phone number is provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checkout completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Receipt.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid checkout request")
    })
    public Receipt checkout(
            @Parameter(description = "Basket and optional loyalty phone number", required = true)
            @RequestBody CheckoutRequest request) {

        ArrayList<String> basket = request.getBasket() == null ? new ArrayList<>() : request.getBasket();
        String phoneNumber = request.getPhoneNumber() == null ? "" : request.getPhoneNumber();
        return checkoutService.checkout(basket, phoneNumber);
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
