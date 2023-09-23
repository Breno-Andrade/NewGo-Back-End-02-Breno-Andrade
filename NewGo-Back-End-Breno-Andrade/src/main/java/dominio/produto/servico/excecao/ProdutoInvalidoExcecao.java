package dominio.produto.servico.excecao;

public class ProdutoInvalidoExcecao extends IllegalArgumentException {
    public ProdutoInvalidoExcecao(ProdutoInsercaoExcecao mensagemErro) {
        super(mensagemErro.getMensagem());
    }
}
