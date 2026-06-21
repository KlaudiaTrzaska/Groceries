package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.AddClientRequest;
import org.example.services.LoyaltyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Loyalty", description = "Client loyalty program endpoints")
public class ClientController {

    LoyaltyService loyaltyService;

    public ClientController(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }

    @PostMapping("/addClient")
    @Operation(summary = "Add a new client", description = "Creates a new client in the loyalty program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client added successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid client data")
    })
    public void addClient(
            @Parameter(description = "Client information", required = true)
            @RequestBody AddClientRequest request) {
        loyaltyService.addClient(request.getName(), request.getPhoneNumber());
//        return "Client " + request.getName() + " added!";
    }

    @GetMapping(value = "/points/{phoneNumber}")
    @Operation(summary = "Get client loyalty points", description = "Retrieves the current loyalty points for a client by phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Points retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public String checkClientPoints(
            @Parameter(description = "Phone number of the client", required = true)
            @PathVariable String phoneNumber) {
        return loyaltyService.checkPointsForClient(phoneNumber);
    }

    @GetMapping(value = "/prizes/{phoneNumber}")
    @Operation(summary = "Get client prizes", description = "Retrieves the list of prizes available for a client by phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prizes retrieved successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public List<String> checkClientPrizes(
            @Parameter(description = "Phone number of the client", required = true)
            @PathVariable String phoneNumber) {
        return loyaltyService.checkPrizesForClient(phoneNumber);
    }

    @PostMapping(value = "/prizes/{phoneNumber}/{prizeName}")
    @Operation(summary = "Get a prize for client", description = "Claims a prize for a client by phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prize claimed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Client does not have enough points"),
            @ApiResponse(responseCode = "404", description = "Prize not found in inventory"),
            @ApiResponse(responseCode = "409", description = "Prize is out of stock",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
    })
    public String getPrizeForClient(
            @Parameter(description = "Phone number of the client", required = true)
            @PathVariable String phoneNumber,
            @Parameter(description = "Name of the prize", required = true)
            @PathVariable String prizeName) {
        return loyaltyService.getPrizeForClient(phoneNumber, prizeName);
    }
}
