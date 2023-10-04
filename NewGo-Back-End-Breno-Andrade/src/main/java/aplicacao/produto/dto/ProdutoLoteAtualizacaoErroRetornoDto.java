package aplicacao.produto.dto;

import java.util.UUID;

public class ProdutoLoteAtualizacaoErroRetornoDto {
    private UUID hash;
    private double quantidade;
    private String status;
    private String mensagem;

    public ProdutoLoteAtualizacaoErroRetornoDto(UUID hash, double quantidade, String status, String mensagem) {
        this.hash = hash;
        this.quantidade = quantidade;
        this.status = status;
        this.mensagem = mensagem;
    }

    public UUID getHash() {
        return hash;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public String getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }
}
