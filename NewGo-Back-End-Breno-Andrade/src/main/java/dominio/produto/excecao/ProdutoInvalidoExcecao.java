package dominio.produto.excecao;

import aplicacao.produto.dto.ProdutoAtualizacaoDto;

public class ProdutoInvalidoExcecao extends IllegalArgumentException {
    public ProdutoInvalidoExcecao(ProdutoInsercaoExcecao mensagemErro) {
        super(mensagemErro.getMensagem());
    }

    public ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao mensagemErro){
        super(mensagemErro.getMensagem());
    }
}
