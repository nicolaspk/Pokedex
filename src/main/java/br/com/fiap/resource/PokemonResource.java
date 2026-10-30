package br.com.fiap.resource;

import br.com.fiap.bo.PokemonBO;
import br.com.fiap.to.PokemonTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pokemon")
public class PokemonResource {
    private final PokemonBO pokemonBO = new PokemonBO();

    @GetMapping
    public ResponseEntity<List<PokemonTO>> findAll() {
        return ResponseEntity.ok(pokemonBO.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        PokemonTO pokemon = pokemonBO.findByCodigo(codigo);
        return pokemon != null ? ResponseEntity.ok(pokemon)
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon não encontrado!");
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PokemonTO pokemon) {
        try {
            PokemonTO salvo = pokemonBO.save(pokemon);
            return salvo != null ? ResponseEntity.status(HttpStatus.CREATED).body(salvo)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível cadastrar");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo, @RequestBody @Valid PokemonTO pokemon) {
        try {
            pokemon.setCodigo(codigo);
            PokemonTO atualizado = pokemonBO.update(pokemon);
            return atualizado != null ? ResponseEntity.ok(atualizado)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon não encontrado!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> delete(@PathVariable Long codigo) {
        return pokemonBO.delete(codigo) ? ResponseEntity.noContent().build()
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon não encontrado!");
    }
}
