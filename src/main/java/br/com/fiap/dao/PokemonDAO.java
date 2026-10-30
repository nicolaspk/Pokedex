package br.com.fiap.dao;

import br.com.fiap.to.PokemonTO;
import java.sql.*;
import java.util.ArrayList;

public class PokemonDAO {
    public ArrayList<PokemonTO> findAll() {
        ArrayList<PokemonTO> pokemons = new ArrayList<>();
        String sql = "SELECT CODIGO, NOME, TIPO, NIVEL, DATA_DE_CAPTURA FROM DDD_POKEMONS ORDER BY CODIGO";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) pokemons.add(montarPokemon(rs));
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao consultar Pokémon", e);
        }
        return pokemons;
    }

    public PokemonTO findByCodigo(Long codigo) {
        String sql = "SELECT CODIGO, NOME, TIPO, NIVEL, DATA_DE_CAPTURA FROM DDD_POKEMONS WHERE CODIGO = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? montarPokemon(rs) : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar Pokémon", e);
        }
    }

    public PokemonTO save(PokemonTO pokemon) {
        String sql = "INSERT INTO DDD_POKEMONS (NOME, TIPO, NIVEL, DATA_DE_CAPTURA) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"CODIGO"})) {
            preencher(ps, pokemon);
            if (ps.executeUpdate() == 0) return null;
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) pokemon.setCodigo(chaves.getLong(1));
                else throw new SQLException("O Oracle não retornou o código gerado");
            }
            return pokemon;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao cadastrar Pokémon", e);
        }
    }

    public PokemonTO update(PokemonTO pokemon) {
        String sql = "UPDATE DDD_POKEMONS SET NOME = ?, TIPO = ?, NIVEL = ?, DATA_DE_CAPTURA = ? WHERE CODIGO = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            preencher(ps, pokemon);
            ps.setLong(5, pokemon.getCodigo());
            return ps.executeUpdate() > 0 ? pokemon : null;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar Pokémon", e);
        }
    }

    public boolean delete(Long codigo) {
        String sql = "DELETE FROM DDD_POKEMONS WHERE CODIGO = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao excluir Pokémon", e);
        }
    }

    private void preencher(PreparedStatement ps, PokemonTO pokemon) throws SQLException {
        ps.setString(1, pokemon.getNome());
        ps.setString(2, pokemon.getTipo());
        ps.setInt(3, pokemon.getNivel());
        ps.setDate(4, Date.valueOf(pokemon.getDataDeCaptura()));
    }

    private PokemonTO montarPokemon(ResultSet rs) throws SQLException {
        return new PokemonTO(
            rs.getLong("CODIGO"), rs.getString("NOME"), rs.getString("TIPO"),
            rs.getInt("NIVEL"), rs.getDate("DATA_DE_CAPTURA").toLocalDate());
    }
}
