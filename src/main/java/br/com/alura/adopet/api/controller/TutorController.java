package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.tutor.TutorAtualizacaoDto;
import br.com.alura.adopet.api.dto.tutor.TutorCadastroDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService service;

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody @Valid TutorCadastroDto dto) {
        try {
            service.cadastrar(dto);
            return ResponseEntity.ok().body("Solicitado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody @Valid TutorAtualizacaoDto dto) {
        service.atualizar(dto);
        return ResponseEntity.ok().build();
    }

}
