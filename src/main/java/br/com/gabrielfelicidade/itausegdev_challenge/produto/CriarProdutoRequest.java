package br.com.gabrielfelicidade.itausegdev_challenge.produto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CriarProdutoRequest {

    @NotEmpty
    private String nome;

    @NotNull
    private ProdutoCategoria categoria;

    @NotNull
    private BigDecimal precoBase;
}
