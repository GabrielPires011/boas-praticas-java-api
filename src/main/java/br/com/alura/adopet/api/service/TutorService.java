package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.TutorAtualizacaoDto;
import br.com.alura.adopet.api.dto.tutor.TutorCadastroDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    @Transactional
    public void cadastrar(TutorCadastroDto dto) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = repository.existsByEmail(dto.email());

        if (telefoneJaCadastrado || emailJaCadastrado)
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");

        var tutor = new Tutor(dto.email(), dto.nome(), dto.telefone());
        repository.save(tutor);
    }

    @Transactional
    public void atualizar(TutorAtualizacaoDto dto) {
        var tutor = new Tutor(dto.email(), dto.nome(), dto.telefone());
        repository.save(tutor);
    }
}
