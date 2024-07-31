package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.pet.DadosDetalhesPetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.alura.adopet.api.util.Util.isFalse;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<DadosDetalhesPetDto> listarTodosDisponiveis() {
        var pets = repository.findAll();
        var disponiveis = new ArrayList<DadosDetalhesPetDto>();
        for (Pet pet : pets) {
            if (isFalse(pet.getAdotado())) {
                disponiveis.add(new DadosDetalhesPetDto(pet));
            }
        }
        return disponiveis;
    }
}
