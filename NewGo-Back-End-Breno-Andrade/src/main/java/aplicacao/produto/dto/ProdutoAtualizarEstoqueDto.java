package aplicacao.produto.dto;

import java.util.UUID;

public class ProdutoAtualizarEstoqueDto {
    private UUID hash;
    private double quantidade;

    public ProdutoAtualizarEstoqueDto(UUID hash, double quantidade) {
        this.hash = hash;
        this.quantidade = quantidade;
    }

    public UUID getHash() {
        return hash;
    }

    public double getQuantidade() {
        return quantidade;
    }
}
