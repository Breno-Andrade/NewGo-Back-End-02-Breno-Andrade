package aplicacao.produto.dto;

import java.util.UUID;

public class ProdutoAtualizacaoPrecoErroRetornoDto {
    private UUID hash;

    private String operacao;
    private double valor;
    private String status;
    private String mensagem;

    public ProdutoAtualizacaoPrecoErroRetornoDto(UUID hash, String operacao, double valor, String status, String mensagem) {
        this.hash = hash;
        this.operacao = operacao;
        this.valor = valor;
        this.status = status;
        this.mensagem = mensagem;
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

    public String getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }
}
