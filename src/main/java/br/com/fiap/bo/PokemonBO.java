package br.com.fiap.bo;

import br.com.fiap.dao.PokemonDAO;
import br.com.fiap.to.PokemonTO;
import java.time.LocalDate;
import java.util.ArrayList;

public class PokemonBO {
    private final PokemonDAO pokemonDAO = new PokemonDAO();

    public ArrayList<PokemonTO> findAll() {
        return pokemonDAO.findAll();
    }
    public PokemonTO findByCodigo(Long codigo) {
        return pokemonDAO.findByCodigo(codigo);
    }
    public PokemonTO save(PokemonTO pokemon) {
        validarData(pokemon);
        return pokemonDAO.save(pokemon);
    }
    public PokemonTO update(PokemonTO pokemon) {
        validarData(pokemon);
        return pokemonDAO.update(pokemon);
    }
    public boolean delete(Long codigo) {
        return pokemonDAO.delete(codigo);
    }
    private void validarData(PokemonTO pokemon) {
        if (pokemon.getDataDeCaptura() == null || !pokemon.getDataDeCaptura().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de captura precisa ser anterior a hoje");
        }
    }
}
