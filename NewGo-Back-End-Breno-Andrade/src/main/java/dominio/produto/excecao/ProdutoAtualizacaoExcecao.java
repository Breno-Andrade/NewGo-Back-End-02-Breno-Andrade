package dominio.produto.excecao;

public enum ProdutoAtualizacaoExcecao {
    ID_INVALIDO("Você não pode alterar o id cadastrado"),
    HASH_INVALIDO("Você não pode alterar o hash cadastrado"),
    NOME_INVALIDO ("Você não pode alterar o nome cadastrado"),
    EAN13_INVALIDO("Você não pode alterar o código ean13 cadastrado"),
    DTCREATE_INVALIDO("Você não pode alterar a data de cadastrado"),
    DTUPDATE_INVALIDO("Você não pode alterar a data de atualização"),
    LATIVO_INVALIDO("Você não pode alterar um produto desativado"),
    ESTOQUE_NEGATIVO("O estoque não pode ficar negativo"),
    PRECO_NEGATIVO("O preço não pode ficar negativo");

    public String mensagem;
    ProdutoAtualizacaoExcecao(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
