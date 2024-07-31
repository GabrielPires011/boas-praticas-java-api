package br.com.alura.adopet.api.validacoes.adocao.solicitacao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.alura.adopet.api.util.Util.isEquals;

@Component
public class ValidacaoTutorComLimiteDeAdocoes implements ValidacaoSolicitacaoAdocao {
    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        var adocoes = adocaoRepository.findAll();
        var tutor = tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor());

        var contador = 0;
        for (var a : adocoes) {
            if (isEquals(a.getTutor(), tutor) && isEquals(a.getStatus(), StatusAdocao.AGUARDANDO_AVALIACAO))
                if (isEquals(a.getTutor(), (tutor)) && isEquals(a.getStatus(), StatusAdocao.APROVADO)) contador += 1;
            if (isEquals(contador, 5))
                throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }
    }
}
