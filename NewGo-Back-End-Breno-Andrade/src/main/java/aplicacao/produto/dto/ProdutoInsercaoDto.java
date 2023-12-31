package aplicacao.produto.dto;

public class ProdutoInsercaoDto {
    private String nome;
    private String descricao;
    private String ean13;
    private double preco;
    private double quantidade;

    private double estoque_min;

    public ProdutoInsercaoDto(String nome, String descricao, String ean13, double preco, double quantidade, double estoque_min) {
        this.nome = nome;
        this.descricao = descricao;
        this.ean13 = ean13;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoque_min = estoque_min;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEan13() {
        return ean13;
    }

    public double getPreco() {
        return preco;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public double getEstoque_min() {
        return estoque_min;
    }
}
