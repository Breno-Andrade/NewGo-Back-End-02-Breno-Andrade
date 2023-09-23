package dominio.produto.servico.excecao;

public enum ProdutoInsercaoExcecao {
    NOME_VAZIO("Você deve inserir o nome do produto."),
    NOME_DUPLICADO("O nome do produto já existe, insira outro nome e tente novamente."),
    EAN13_DUPLICADO("O código ean13 do produto já existe, insira outro código e tente novamente."),
    PRECO_NEGATIVO("O preço do produto não pode ser negativo."),
    QUANTIDADE_NEGATIVA("A quantidade do produto não pode ser negativo."),
    ESTOQUE_MIN_NEGATIVO("O estoque mínimo do produto não pode ser negativo.");

    public String mensagem;
    ProdutoInsercaoExcecao(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
