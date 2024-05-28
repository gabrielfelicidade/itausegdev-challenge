package br.com.gabrielfelicidade.itausegdev_challenge.produto.impl;

import br.com.gabrielfelicidade.itausegdev_challenge.produto.CriarProdutoRequest;
import br.com.gabrielfelicidade.itausegdev_challenge.produto.Produto;
import br.com.gabrielfelicidade.itausegdev_challenge.produto.ProdutoCategoria;
import br.com.gabrielfelicidade.itausegdev_challenge.produto.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class ProdutoServiceImpl implements ProdutoService {

    protected static final int PRECO_SCALE = 2;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto criarProduto(final CriarProdutoRequest request) {
        log.info("Recebida requisição para criação de produto, {}", request);

        final BigDecimal precoBase = aplicarEscala(request.getPrecoBase());
        final BigDecimal precoTarifado = aplicarEscala(calcularPrecoTarifado(request));
        final Produto produto = Produto.builder()
                .categoria(request.getCategoria())
                .nome(request.getNome())
                .precoBase(precoBase)
                .precoTarifado(precoTarifado)
                .build();
        final Produto produtoPersistido = produtoRepository.save(produto);

        log.info("Produto criado, {}", produtoPersistido);

        return produtoPersistido;
    }

    private BigDecimal calcularPrecoTarifado(final CriarProdutoRequest request) {
        final ProdutoCategoria categoria = request.getCategoria();
        final BigDecimal valorIof = calcularPorcentagem(request.getPrecoBase(), categoria.getPorcentagemIof());
        final BigDecimal valorPis = calcularPorcentagem(request.getPrecoBase(), categoria.getPorcentagemPis());
        final BigDecimal valorCofins = calcularPorcentagem(request.getPrecoBase(), categoria.getPorcentagemCofins());

        return request.getPrecoBase()
                .add(valorIof)
                .add(valorPis)
                .add(valorCofins);
    }

    private BigDecimal calcularPorcentagem(final BigDecimal preco, final BigDecimal porcentagem) {
        final BigDecimal porcentagemCorrigida = porcentagem
                .divide(BigDecimal.valueOf(100));

        return preco.multiply(porcentagemCorrigida);
    }

    private BigDecimal aplicarEscala(final BigDecimal valor) {
        return valor.setScale(PRECO_SCALE, RoundingMode.DOWN);
    }
}
