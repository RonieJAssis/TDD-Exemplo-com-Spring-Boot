package com.example.projetotdd.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.projetotdd.model.Produto;
import com.example.projetotdd.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Option;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest
public class ProdutoControllerTest {
    //MookMvc
    //Mockito
    //Mock

    @Autowired
    private ProdutoController produtoController;

    @MockBean
    private ProdutoService produtoService;
    
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        //Aqui será executado antes de qlquer caso de teste.
        this.mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }
    @Test
    public void deve_retornar_status_200_ok_ao_chamar_o_metodo_obter_todos() throws Exception{
        //Arrange = Preparacao
        List<Produto> produtos = new ArrayList<Produto>();
        var requestBuilder = MockMvcRequestBuilders.get("/api/produtos");
        when(this.produtoService.obterTodos()).thenReturn(produtos);
        //Act = Acao
        this.mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
        //Assert = Confirmacao
    }
    @Test
    public void deve_retornar_o_produto_por_id() throws Exception{
        //Arrange = Preparacao
        Produto produto = new Produto();
        produto.setId(2l);
        produto.setNome("Martelo");
        produto.setQuantidade(10);
        Optional<Produto> optProduto = Optional.of(produto);
        var requestBuilder = MockMvcRequestBuilders.get("/api/produtos/2");
        when(this.produtoService.obterPorId(2l)).thenReturn(optProduto);
        //Act = Acao
        this.mockMvc.perform(requestBuilder)
        //Assert = Confirmacao
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2l));
    }
    @Test
    public void deve_adicionar_o_produto() throws Exception{
        //Arrange = Preparacao
        Produto produto = new Produto();
        produto.setNome("Martelo");
        produto.setQuantidade(10);
        String json = new ObjectMapper().writeValueAsString(produto);
        var requestBuilder = MockMvcRequestBuilders.post("/api/produtos").content(json).contentType(MediaType.APPLICATION_JSON_VALUE);
        produto.setId(1l);
        when(this.produtoService.adicionar(produto)).thenReturn(produto);
        //Act = Acao
        this.mockMvc.perform(requestBuilder)
        //Assert = Confirmacao
        .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
