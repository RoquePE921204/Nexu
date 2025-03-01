package com.nexu.restapi.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandResponse implements Serializable {

    private Long id;

    private String nombre;

    @JsonProperty("average_price")
    private Double averagePrice;

}
