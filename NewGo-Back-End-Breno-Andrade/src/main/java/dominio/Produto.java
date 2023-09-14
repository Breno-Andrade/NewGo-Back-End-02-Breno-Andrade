package dominio;

import java.sql.Date;
import java.sql.Timestamp;
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
    private Timestamp dtcreate;
    private Timestamp dtupdate;
    private boolean lativo;

    public Produto(String nome, String descricao, String ean13, double preco, double quantidade, double estoque_min) {
        setNome(nome);
        this.descricao = descricao;
        setEan13(ean13);
        setPreco(preco);
        setQuantidade(quantidade);
        setEstoque_min(estoque_min);
    }

    public Produto(long id, UUID hash, String nome, String descricao, String ean13, double preco, double quantidade, double estoque_min, Timestamp dtcreate, Timestamp dtupdate, boolean lativo) {
        this(nome, descricao, ean13, preco, quantidade, estoque_min);

        this.id = id;
        this.hash = hash;
        this.dtcreate = dtcreate;
        this.dtupdate = dtupdate;
        this.lativo = lativo;
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
        if (nome.isEmpty() || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Você deve inserir um nome para o produto.");
        }
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
        if ((ean13.length() != 13)){
            throw new IllegalArgumentException("O código ean13 deve conter 13 caracteres.");
        }
        this.ean13 = ean13;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0){
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
        this.preco = preco;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        if (quantidade < 0){
            throw new IllegalArgumentException("A quantidade não pode ser negativa");
        }
        this.quantidade = quantidade;
    }

    public double getEstoque_min() {
        return estoque_min;
    }

    public void setEstoque_min(double estoque_min) {
        if (estoque_min < 0){
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa");
        }
        this.estoque_min = estoque_min;
    }

    public Timestamp getDtcreate() {
        return dtcreate;
    }

    public void setDtcreate(Timestamp dtcreate) {
        this.dtcreate = dtcreate;
    }

    public Timestamp getDtupdate() {
        return dtupdate;
    }

    public void setDtupdate(Timestamp dtupdate) {
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
