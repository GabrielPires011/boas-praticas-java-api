package br.com.alura.adopet.api.validacoes.adocao.solicitacao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.alura.adopet.api.util.Util.isEquals;

@Component
public class ValidacaoPetComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {
    @Autowired
    private AdocaoRepository adocaoRepository;
    @Autowired
    PetRepository petRepository;
    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        var adocoes = adocaoRepository.findAll();
        var pet = petRepository.getReferenceById(solicitacaoAdocaoDto.idPet());

        for (var a : adocoes) {
            if (isEquals(a.getPet(), pet) && isEquals(a.getStatus(), (StatusAdocao.AGUARDANDO_AVALIACAO)))
                throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
        }
    }
}
