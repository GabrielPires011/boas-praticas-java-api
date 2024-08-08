package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService service;

    @Test
    @DisplayName("Deveria devolver codigo 400 para solicitacao de adocao com erros")
    void deveriaRetornaCodigo400SolicitacaoDeAdocao() throws Exception {
        var json = "{}";

        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 200 para solicitacao de adocao sem erros")
    void deveriaRetornaCodigo200SolicitacaoDeAdocao() throws Exception {
        var json = """
                        {
                            "idPet": 1,
                            "idTutor": 1,
                            "motivo": "Motivo qualquer"
                        }
                        """;

        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals("Solicitado com sucesso!", response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria devolver codigo 400 para aprovasao de adocao com erros")
    void deveriaRetornaCodigo400AprovasaoDeAdocao() throws Exception {
        var json = "{}";

        var response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 200 para aprovasao de adocao sem erros")
    void deveriaRetornaCodigo200AprovasaoDeAdocao() throws Exception {
        var json = """
                        {
                            "idAdocao": 1
                        }
                        """;

        var response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals("Aprovado com sucesso!", response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria devolver codigo 400 para reprovasao de adocao com erros")
    void deveriaRetornaCodigo400ReprovasaoDeAdocao() throws Exception {
        var json = "{}";

        var response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 200 para reprovasao de adocao sem erros")
    void deveriaRetornaCodigo200ReprovasaoDeAdocao() throws Exception {
        var json = """
                        {
                            "idAdocao": 1,
                            "justificativa": "Exemplo exemplo"
                        }
                        """;

        var response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals("Reprovado com sucesso!", response.getContentAsString());
    }

}