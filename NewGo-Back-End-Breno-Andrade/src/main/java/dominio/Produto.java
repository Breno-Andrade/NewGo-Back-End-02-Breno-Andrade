package dominio;

import java.util.Date;
import java.util.UUID;

public class Produto {
    private long id;
    private UUID hash;
    private String nome;
    private String descricao;
    private String ean13;
    private double preco;
    private double quantidade;
    private double estoque_min;
    private Date dtcreate;
    private Date dtupdate;
    private boolean lativo;

    public Produto(long id, UUID hash, String nome, String descricao, String ean13, double preco, double quantidade, double estoque_min, Date dtcreate, Date dtupdate, boolean lativo) {
        this.id = id;
        this.hash = hash;
        this.nome = nome;
        this.descricao = descricao;
        this.ean13 = ean13;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoque_min = estoque_min;
        this.dtcreate = dtcreate;
        this.dtupdate = dtupdate;
        this.lativo = lativo;
    }

    public Produto(String nome, String descricao, String ean13, double preco, double quantidade, double estoque_min) {
        this.nome = nome;
        this.descricao = descricao;
        this.ean13 = ean13;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoque_min = estoque_min;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getHash() {
        return hash;
    }

    public void setHash(UUID hash) {
        this.hash = hash;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getEstoque_min() {
        return estoque_min;
    }

    public void setEstoque_min(double estoque_min) {
        this.estoque_min = estoque_min;
    }

    public Date getDtcreate() {
        return dtcreate;
    }

    public void setDtcreate(Date dtcreate) {
        this.dtcreate = dtcreate;
    }

    public Date getDtupdate() {
        return dtupdate;
    }

    public void setDtupdate(Date dtupdate) {
        this.dtupdate = dtupdate;
    }

    public boolean isLativo() {
        return lativo;
    }

    public void setLativo(boolean lativo) {
        this.lativo = lativo;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", hash=" + hash +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ean13='" + ean13 + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", estoque_min=" + estoque_min +
                ", dtcreate=" + dtcreate +
                ", dtupdate=" + dtupdate +
                ", lativo=" + lativo +
                '}';
    }
}
