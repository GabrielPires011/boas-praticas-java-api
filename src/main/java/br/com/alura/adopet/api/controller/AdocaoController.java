package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.adocao.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.SolictacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;

import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

    @PostMapping
    public ResponseEntity<String> solicitar(@RequestBody @Valid SolictacaoAdocaoDto solictacaoAdocaoDto) {
        try {
            adocaoService.solicitar(solictacaoAdocaoDto);
            return ResponseEntity.ok().body("Solicitado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/aprovar")
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAdocaoDto aprovacaoAdocaoDto) {
        adocaoService.aprovar(aprovacaoAdocaoDto);
        return ResponseEntity.ok().body("Solicitado com sucesso!");
    }

    @PutMapping("/reprovar")
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovacaoAdocaoDto reprovacaoAdocaoDto) {
        adocaoService.reprovar(reprovacaoAdocaoDto);
        return ResponseEntity.ok().body("Solicitado com sucesso!");
    }
}