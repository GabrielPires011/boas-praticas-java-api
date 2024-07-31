package br.com.alura.adopet.api.validacoes.adocao.solicitacao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.alura.adopet.api.util.Util.isTrue;

@Component
public class ValidacaoPetDisponivel implements ValidacaoSolicitacaoAdocao {
    @Autowired
    PetRepository petRepository;
    
    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        var pet = petRepository.getReferenceById(solicitacaoAdocaoDto.idPet());

        if (isTrue(pet.getAdotado())) throw new ValidacaoException("Pet já foi adotado!");
    }
}
