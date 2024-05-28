package br.com.gabrielfelicidade.itausegdev_challenge.produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoControllerIntegrationTest {

    private static final String PRODUTO_PATH = "/produto";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final EasyRandom random = new EasyRandom();

    @Test
    public void deveCriarProduto() throws Exception {
        final CriarProdutoRequest request = random.nextObject(CriarProdutoRequest.class);
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
                .setScale(2, RoundingMode.DOWN);
        final BigDecimal precoTarifadoEsperado = request.getPrecoBase()
                .add(valorIof)
                .add(valorPis)
                .add(valorCofins)
                .setScale(2, RoundingMode.DOWN);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(PRODUTO_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(request.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria", CoreMatchers.is(request.getCategoria().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco_base").value(precoBaseEsperado.doubleValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco_tarifado").value(precoTarifadoEsperado.doubleValue()));
    }

    @Test
    public void naoDeveCriarProdutoQuandoNomeNaoEstiverPreenchido() throws Exception {
        final CriarProdutoRequest request = random.nextObject(CriarProdutoRequest.class);

        request.setNome(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(PRODUTO_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void naoDeveCriarProdutoQuandoCategoriaNaoEstiverPreenchida() throws Exception {
        final CriarProdutoRequest request = random.nextObject(CriarProdutoRequest.class);

        request.setCategoria(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(PRODUTO_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void naoDeveCriarProdutoQuandoCategoriaInformadaNaoExistir() throws Exception {
        final CriarProdutoRequest request = random.nextObject(CriarProdutoRequest.class);
        final String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(PRODUTO_PATH)
                        .content(content.replace(request.getCategoria().name(), "NAO_EXISTE"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void naoDeveCriarProdutoQuandoPrecoBaseNaoEstiverPreenchido() throws Exception {
        final CriarProdutoRequest request = random.nextObject(CriarProdutoRequest.class);

        request.setPrecoBase(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(PRODUTO_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
