package br.com.fiap.to;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class PokemonTO {
    private Long codigo;
    @NotBlank(message = "Informe o nome")
    @Size(max = 100)
    private String nome;
    @NotBlank(message = "Informe o tipo")
    @Size(max = 50)
    private String tipo;
    @NotNull(message = "Informe o nível")
    @Min(value = 1, message = "O nível mínimo é 1")
    @Max(value = 100, message = "O nível máximo é 100")
    private Integer nivel;
    @NotNull(message = "Informe a data de captura")
    @Past(message = "A data de captura deve ser anterior a hoje")
    private LocalDate dataDeCaptura;

    public PokemonTO() {}
    public PokemonTO(Long codigo, String nome, String tipo, Integer nivel, LocalDate dataDeCaptura) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
        this.nivel = nivel;
        this.dataDeCaptura = dataDeCaptura;
    }
    public Long getCodigo() { return codigo; }
    public void setCodigo(Long codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }
    public LocalDate getDataDeCaptura() { return dataDeCaptura; }
    public void setDataDeCaptura(LocalDate dataDeCaptura) { this.dataDeCaptura = dataDeCaptura; }
}
