package aplicacao.produto.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class ProdutoAtualizacaoDto {
    private long id;
    private UUID hash;
    private String nome;
    private String descricao;
    private String ean13;
    private double preco;
    private double quantidade;
    private double estoque_min;
    private Timestamp dtcreate;
    private Timestamp dtupdate;
    private boolean lativo;

    public ProdutoAtualizacaoDto(String descricao, double preco, double quantidade, double estoque_min) {
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoque_min = estoque_min;
    }

    public ProdutoAtualizacaoDto(long id, UUID hash, String nome, String descricao, String ean13, double preco, double quantidade, double estoque_min, Timestamp dtcreate, Timestamp dtupdate, boolean lativo) {
        this(descricao, preco, quantidade, estoque_min);

        this.id = id;
        this.hash = hash;
        this.nome = nome;
        this.ean13 = ean13;
        this.dtcreate = dtcreate;
        this.dtupdate = dtupdate;
        this.lativo = lativo;
    }

    public long getId() {
        return id;
    }

    public UUID getHash() {
        return hash;
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

    public Timestamp getDtcreate() {
        return dtcreate;
    }

    public Timestamp getDtupdate() {
        return dtupdate;
    }

    public boolean isLativo() {
        return lativo;
    }
}
