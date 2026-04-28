package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for adding a new client")
public class AddClientRequest {

    @Schema(description = "Name of the client", example = "John Doe")
    private String name;

    @Schema(description = "Phone number of the client", example = "+1234567890")
    private String phoneNumber;
}

