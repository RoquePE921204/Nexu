package com.nexu.restapi.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewBrandRequest implements Serializable {

    @NotBlank(message = "El nombre completo es requerido")
    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre no debe contener números ni caracteres especiales")
    private String nombre;
}
