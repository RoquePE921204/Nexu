package com.nexu.restapi.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateModelRequest implements Serializable {

    @NotNull(message = "El precio promedio es obligatorio")
    @Min(value = 100001, message = "El precio promedio debe ser mayor a 100,000")
    @JsonProperty("average_price")
    private Double averagePrice;
}
