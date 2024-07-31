package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.AbrigoDto;
import br.com.alura.adopet.api.dto.pet.DadosDetalhesPetDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static br.com.alura.adopet.api.util.Util.isParsableLong;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;


    public List<AbrigoDto> listar() {
        var abrigos = repository.findAll();

        var abrigosDto = new ArrayList<AbrigoDto>();
        abrigos.forEach(abrigo -> abrigosDto.add(new AbrigoDto(abrigo)));
        return abrigosDto;
    }


    @Transactional
    public void cadastrar(AbrigoDto dto) {
        boolean nomeOuTelefoneOuEmailJaCadastrado = repository
                .existsByNomeOrTelefoneOrNome(dto.nome(), dto.telefone(), dto.email());

        if (nomeOuTelefoneOuEmailJaCadastrado)
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");

        var abrigo = new Abrigo(dto.id(), dto.nome(), dto.telefone(), dto.email());

        repository.save(abrigo);
    }

    public List<DadosDetalhesPetDto> listarPets(String idOuNome) {
        var pets = isParsableLong(idOuNome) ? repository.getReferenceById(Long.parseLong(idOuNome)).getPets() :
                repository.findByNome(idOuNome).getPets();

        var petsDto = new ArrayList<DadosDetalhesPetDto>();
        pets.forEach(pet -> petsDto.add(new DadosDetalhesPetDto(pet)));

        return petsDto;
    }

    @Transactional
    public void cadastrarPet(String idOuNome, PetDto dto) {
        var abrigo = isParsableLong(idOuNome) ? repository.getReferenceById(Long.parseLong(idOuNome)) :
                repository.findByNome(idOuNome);

        var pet = new Pet(dto.tipo(), dto.nome(), dto.raca(), dto.idade(), dto.cor(), dto.peso(), abrigo);
        abrigo.getPets().add(pet);
        repository.save(abrigo);
    }
}
