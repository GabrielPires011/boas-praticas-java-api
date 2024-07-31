package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.adocao.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.SolictacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Transactional
    public void solicitar(SolictacaoAdocaoDto solictacaoAdocaoDto) {

        var tutor = tutorRepository.findById(solictacaoAdocaoDto.idTutor()).orElse(null);
        if (isEmpty(tutor)) throw new ValidacaoException("Tutor não existe!");

        var pet = petRepository.findById(solictacaoAdocaoDto.idPet()).orElse(null);
        if (isEmpty(pet)) throw new ValidacaoException("Pet não existe!");

        var erro = verificarSolicitacao(pet, tutor);

        if (isNotEmpty(erro)) throw new ValidacaoException(erro);

        var adocao = new Adocao();
        adocao.setMotivo(solictacaoAdocaoDto.motivo());
        adocao.setPet(pet);
        adocao.setTutor(tutor);
        adocao.setData(LocalDateTime.now());
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        repository.save(adocao);

        emailService.sendEmail("adopet@email.com.br", adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção", "Olá " + adocao.getPet().getAbrigo().getNome() +
                        "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() +
                        ". \nFavor avaliar para aprovação ou reprovação.");
    }

    @Transactional
    public void aprovar(AprovacaoAdocaoDto aprovacaoAdocaoDto) {
        var adocao = repository.findById(aprovacaoAdocaoDto.idAdocao()).orElse(null);

        if (isEmpty(adocao)) throw new ValidacaoException("Adoção não existe!");
        if (isEquals(adocao.getStatus(), StatusAdocao.APROVADO))
            throw new ValidacaoException("A adoção já está aprovada!");

        adocao.setStatus(StatusAdocao.APROVADO);
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
        var adocao = repository.findById(reprovacaoAdocaoDto.idAdocao()).orElse(null);

        if (isEmpty(adocao)) throw new ValidacaoException("Adoção não existe!");
        if (isEquals(adocao.getStatus(), StatusAdocao.REPROVADO))
            throw new ValidacaoException("A adoção já está reprovada!");

        adocao.setStatus(StatusAdocao.REPROVADO);
        repository.save(adocao);

        emailService.sendEmail("adopet@email.com.br", adocao.getTutor().getEmail(),
                "Adoção reprovada", "Olá " + adocao.getTutor().getNome() +
                        "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " +
                        adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() +
                        " com a seguinte justificativa: " + adocao.getJustificativaStatus());
    }

    private String verificarSolicitacao(Pet pet, Tutor tutor) {
        if (isTrue(pet.getAdotado())) throw new ValidacaoException("Pet já foi adotado!");

        var adocoes = repository.findAll();

        var contador = 0;
        for (Adocao a : adocoes) {
            if (isEquals(a.getTutor(), tutor) && isEquals(a.getStatus(), StatusAdocao.AGUARDANDO_AVALIACAO))
                return "Tutor já possui outra adoção aguardando avaliação!";
            if (isEquals(a.getPet(), (pet)) && isEquals(a.getStatus(), (StatusAdocao.AGUARDANDO_AVALIACAO)))
                return "Pet já está aguardando avaliação para ser adotado!";
            if (isEquals(a.getTutor(), (tutor)) && isEquals(a.getStatus(), StatusAdocao.APROVADO)) contador += 1;
            if (isEquals(contador, 5))
                return "Tutor chegou ao limite máximo de 5 adoções!";
        }

        return null;
    }
}