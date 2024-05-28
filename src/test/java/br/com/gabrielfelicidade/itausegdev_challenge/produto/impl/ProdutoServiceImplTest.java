package br.com.gabrielfelicidade.itausegdev_challenge.produto.impl;

import br.com.gabrielfelicidade.itausegdev_challenge.produto.CriarProdutoRequest;
import br.com.gabrielfelicidade.itausegdev_challenge.produto.Produto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.com.gabrielfelicidade.itausegdev_challenge.produto.impl.ProdutoServiceImpl.PRECO_SCALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceImplTest {

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private final EasyRandom random = new EasyRandom();

    @Test
    public void deveCriarProduto() {
        final CriarProdutoRequest request = random.nextObject(CriarProdutoRequest.class);

        when(produtoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        final Produto produto = produtoService.criarProduto(request);

        verify(produtoRepository).save(any());

        final BigDecimal porcentagemIof = request.getCategoria().getPorcentagemIof();
        final BigDecimal valorIof = request.getPrecoBase()
                .multiply(porcentagemIof.divide(BigDecimal.valueOf(100)));
        final BigDecimal porcentagemPis = request.getCategoria().getPorcentagemPis();
        final BigDecimal valorPis = request.getPrecoBase()
                .multiply(porcentagemPis.divide(BigDecimal.valueOf(100)));
        final BigDecimal porcentagemCofins = request.getCategoria().getPorcentagemCofins();
        final BigDecimal valorCofins = request.getPrecoBase()
                .multiply(porcentagemCofins.divide(BigDecimal.valueOf(100)));
        final BigDecimal precoBaseEsperado = request.getPrecoBase()
                .setScale(PRECO_SCALE, RoundingMode.DOWN);
        final BigDecimal precoTarifadoEsperado = request.getPrecoBase()
                .add(valorIof)
                .add(valorPis)
                .add(valorCofins)
                .setScale(PRECO_SCALE, RoundingMode.DOWN);

        assertThat(produto)
                .isNotNull();
        assertThat(produto.getCategoria())
                .isEqualTo(request.getCategoria());
        assertThat(produto.getNome())
                .isEqualTo(request.getNome());
        assertThat(produto.getPrecoBase())
                .isEqualTo(precoBaseEsperado);
        assertThat(produto.getPrecoTarifado())
                .isEqualTo(precoTarifadoEsperado);
    }
}