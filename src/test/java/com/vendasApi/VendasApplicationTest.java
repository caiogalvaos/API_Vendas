package com.vendasApi;

import com.vendasApi.Controller.CadastroDeProdutosController;
import com.vendasApi.DTO.ProdutoDTO;
import com.vendasApi.Service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(CadastroDeProdutosController.class)
@ActiveProfiles("test")
public class VendasApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @InjectMocks
    private CadastroDeProdutosController cadastroDeProdutosController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void contextLoads() {
        Assertions.assertNotNull(cadastroDeProdutosController);
    }

    @Test
    void deveCadastrarNovoProdutoComSucesso() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto Teste", BigDecimal.valueOf(100.00));

        Mockito.doNothing().when(produtoService).CadastrarProduto(produtoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void deveRetornarErroQuandoNomeDoProdutoEstiverVazio() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO("", BigDecimal.valueOf(100.00));

        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    void deveRetornarErroQuandoPrecoDoProdutoForNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto Teste", BigDecimal.valueOf(-10.00));

        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }
}
