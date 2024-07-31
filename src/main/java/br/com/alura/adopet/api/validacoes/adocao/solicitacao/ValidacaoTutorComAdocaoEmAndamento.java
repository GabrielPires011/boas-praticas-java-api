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
public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {
    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        var adocoes = adocaoRepository.findAll();
        var tutor = tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor());

        for (var a : adocoes) {
            if (isEquals(a.getTutor(), tutor) && isEquals(a.getStatus(), StatusAdocao.AGUARDANDO_AVALIACAO))
                throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
        }
    }
}
