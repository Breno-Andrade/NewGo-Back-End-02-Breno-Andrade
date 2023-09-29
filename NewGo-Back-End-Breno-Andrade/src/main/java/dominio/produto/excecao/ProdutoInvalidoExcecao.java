package dominio.produto.excecao;

import aplicacao.produto.dto.ProdutoAtualizacaoDto;

public class ProdutoInvalidoExcecao extends RuntimeException {
    public ProdutoInvalidoExcecao(ProdutoInsercaoExcecao mensagemErro) {
        super(mensagemErro.getMensagem());
    }

    public ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao mensagemErro){
        super(mensagemErro.getMensagem());
    }

    public ProdutoInvalidoExcecao(ProdutoRequisicaoExcecao mensagemErro){
        super(mensagemErro.getMensagem());
    }
}
