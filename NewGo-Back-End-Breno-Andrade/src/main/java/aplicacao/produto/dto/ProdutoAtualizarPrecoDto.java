package aplicacao.produto.dto;

import java.util.UUID;

public class ProdutoAtualizarPrecoDto {
    private UUID hash;
    private String operacao;
    private double valor;

    public ProdutoAtualizarPrecoDto(UUID hash, String operacao, double valor) {
        this.hash = hash;
        this.operacao = operacao;
        this.valor = valor;
    }

    public UUID getHash() {
        return hash;
    }

    public String getOperacao() {
        return operacao;
    }

    public double getValor() {
        return valor;
    }
}
