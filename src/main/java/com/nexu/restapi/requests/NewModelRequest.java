package com.nexu.restapi.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewModelRequest implements Serializable {

    @NotBlank(message = "El nombre completo es requerido")
    @Pattern(regexp = "^[A-Za-z0-9\\+\\-\\/*\\s]+$", message = "El nombre no debe contener números ni caracteres especiales")
    private String name;

    @NotNull(message = "El precio promedio es obligatorio")
    @Min(value = 100001, message = "El precio promedio debe ser mayor a 100,000")
    @JsonProperty("average_price")
    private Double averagePrice;
}
