package br.com.gabrielfelicidade.itausegdev_challenge.produto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum ProdutoCategoria {

    VIDA(BigDecimal.ONE, BigDecimal.valueOf(2.2), BigDecimal.ZERO),
    AUTO(BigDecimal.valueOf(5.5), BigDecimal.valueOf(4), BigDecimal.ONE),
    VIAGEM(BigDecimal.TWO, BigDecimal.valueOf(4), BigDecimal.ONE),
    RESIDENCIAL(BigDecimal.valueOf(4), BigDecimal.ZERO, BigDecimal.valueOf(3)),
    PATRIMONIAL(BigDecimal.valueOf(5), BigDecimal.valueOf(3), BigDecimal.ZERO);

    private final BigDecimal porcentagemIof;
    private final BigDecimal porcentagemPis;
    private final BigDecimal porcentagemCofins;

    ProdutoCategoria(BigDecimal porcentagemIof, BigDecimal porcentagemPis, BigDecimal porcentagemCofins) {
        this.porcentagemIof = porcentagemIof;
        this.porcentagemPis = porcentagemPis;
        this.porcentagemCofins = porcentagemCofins;
    }
}
