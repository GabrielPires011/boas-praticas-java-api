package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.adocao.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static br.com.alura.adopet.api.util.Util.*;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<ValidacaoSolicitacaoAdocao> validacoes;

    @Transactional
    public void solicitar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        var tutor = tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor());
        var pet = petRepository.getReferenceById(solicitacaoAdocaoDto.idPet());

        validacoes.forEach(v -> v.validar(solicitacaoAdocaoDto));

        var adocao = new Adocao(tutor, pet, solicitacaoAdocaoDto.motivo());

        repository.save(adocao);

        emailService.sendEmail("adopet@email.com.br", adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção", "Olá " + adocao.getPet().getAbrigo().getNome() +
                        "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() +
                        ". \nFavor avaliar para aprovação ou reprovação.");
    }

    @Transactional
    public void aprovar(AprovacaoAdocaoDto aprovacaoAdocaoDto) {
        var adocao = repository.getReferenceById(aprovacaoAdocaoDto.idAdocao());

        if (isEquals(adocao.getStatus(), StatusAdocao.APROVADO))
            throw new ValidacaoException("A adoção já está aprovada!");

        adocao.marcaComoAprovado();
        repository.save(adocao);

        emailService.sendEmail("adopet@email.com.br", adocao.getTutor().getEmail(),
                "Adoção aprovada", "Parabéns " + adocao.getTutor().getNome() + "!\n\nSua adoção do pet " +
                        adocao.getPet().getNome() + ", solicitada em " +
                        adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", foi aprovada.\nFavor entrar em contato com o abrigo " +
                        adocao.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet.");
    }

    @Transactional
    public void reprovar(ReprovacaoAdocaoDto reprovacaoAdocaoDto) {
        var adocao = repository.getReferenceById(reprovacaoAdocaoDto.idAdocao());

        if (isEquals(adocao.getStatus(), StatusAdocao.REPROVADO) &&
                isEquals(adocao.getJustificativaStatus(), reprovacaoAdocaoDto.justificativa()))
            throw new ValidacaoException("A adoção já está reprovada!");

        adocao.marcaComoReprovado(reprovacaoAdocaoDto.justificativa());
        repository.save(adocao);

        emailService.sendEmail("adopet@email.com.br", adocao.getTutor().getEmail(),
                "Adoção reprovada", "Olá " + adocao.getTutor().getNome() +
                        "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " +
                        adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() +
                        " com a seguinte justificativa: " + adocao.getJustificativaStatus());
    }
}