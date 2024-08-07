package br.com.alura.adopet.api.validacoes.adocao.solicitacao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {
    @Mock
    AdocaoRepository adocaoRepository;
    @InjectMocks
    ValidacaoPetComAdocaoEmAndamento validacao;

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoPet() {
        var dto  = new SolicitacaoAdocaoDto(7L, 2L,"Motivo qualquer");

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoPet() {
        var dto  = new SolicitacaoAdocaoDto(7L, 2L,"Motivo qualquer");

        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(anyLong(), any())).willReturn(true);

        assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}