package br.com.alura.adopet.api.dto.abrigo;

import br.com.alura.adopet.api.model.Abrigo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AbrigoDto(Long id,
                        @NotBlank String nome,
                        @NotBlank @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}") String telefone,
                        @NotBlank @Email String email) {

    public AbrigoDto(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
    }
}
