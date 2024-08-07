package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import br.com.alura.adopet.api.validacoes.adocao.solicitacao.ValidacaoPetComAdocaoEmAndamento;
import br.com.alura.adopet.api.validacoes.adocao.solicitacao.ValidacaoPetDisponivel;
import br.com.alura.adopet.api.validacoes.adocao.solicitacao.ValidacaoTutorComAdocaoEmAndamento;
import br.com.alura.adopet.api.validacoes.adocao.solicitacao.ValidacaoTutorComLimiteDeAdocoes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService service;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ValidacaoPetComAdocaoEmAndamento validacaoPetComAdocaoEmAndamento;

    @Mock
    private ValidacaoPetDisponivel validacaoPetDisponivel;

    @Mock
    private ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Mock
    private ValidacaoTutorComLimiteDeAdocoes validacaoTutorComLimiteDeAdocoes;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Test
    void deveriaSalvarAdocaoAoSolicitar() {
        arrangeSolicitar();

        service.solicitar(dto);

        then(repository).should().save(adocaoCaptor.capture());
        var adocao = adocaoCaptor.getValue();
        assertEquals(pet, adocao.getPet());
        assertEquals(tutor, adocao.getTutor());
        assertEquals(dto.motivo(), adocao.getMotivo());
    }

    @Test
    void deveriaChamarValidadoresDeAdocaoAoSolicitar() {
        arrangeSolicitar();

        validacoes.add(validacaoPetComAdocaoEmAndamento);
        validacoes.add(validacaoPetDisponivel);
        validacoes.add(validacaoTutorComAdocaoEmAndamento);
        validacoes.add(validacaoTutorComLimiteDeAdocoes);

        service.solicitar(dto);

        BDDMockito.then(validacaoPetComAdocaoEmAndamento).should().validar(dto);
        BDDMockito.then(validacaoPetDisponivel).should().validar(dto);
        BDDMockito.then(validacaoTutorComAdocaoEmAndamento).should().validar(dto);
        BDDMockito.then(validacaoTutorComLimiteDeAdocoes).should().validar(dto);
    }

    void arrangeSolicitar() {
        dto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo qualquer");

        given(petRepository.getReferenceById(any())).willReturn(pet);
        given(tutorRepository.getReferenceById(any())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
    }
}