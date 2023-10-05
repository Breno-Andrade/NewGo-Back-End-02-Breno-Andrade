package dominio.produto.excecao;

public enum ProdutoRequisicaoExcecao {
    PRODUTO_NAO_ENCONTRADO("Não foi possível encontrar um produto com o hash fornecido."),
    PRODUTO_INATIVO("O produto consultado está desativado.");

    public String mensagem;
    ProdutoRequisicaoExcecao(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
