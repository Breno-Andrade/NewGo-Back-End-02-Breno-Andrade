package aplicacao.produto.dto;

import dominio.produto.excecao.ProdutoRequisicaoExcecao;

import java.util.UUID;

public class ProdutoRequisicaoDto {
    private UUID hash;

    public ProdutoRequisicaoDto(UUID hash){
        this.hash = hash;
    }

    public UUID getHash() {
        return hash;
    }
}
