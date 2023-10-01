package aplicacao.produto.dto;

import dominio.produto.servico.ProdutoLativoServico;

import java.util.UUID;

public class ProdutoLativoDto {
    private boolean lativo;

    public ProdutoLativoDto(boolean lativo){
        this.lativo = lativo;
    };

    public boolean isLativo() {
        return lativo;
    }
}

