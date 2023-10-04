package infraestrutura.produto.dao;

import infraestrutura.produto.conexao.GeradorConexao;
import infraestrutura.produto.entidade.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdutoDAO {
    private GeradorConexao gerador = new GeradorConexao();
    private Connection conexao = gerador.gerarConexao();

    public Produto inserirNovoProduto(Produto produto) {
        try {
            String insertSql = "INSERT INTO produto (hash, nome, descricao, ean13, preco, quantidade, estoque_min, dtcreate, lativo)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement comandoComConexao = conexao.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            comandoComConexao.setObject(1, produto.getHash());
            comandoComConexao.setString(2, produto.getNome());
            comandoComConexao.setString(3, produto.getDescricao());
            comandoComConexao.setString(4, produto.getEan13());
            comandoComConexao.setDouble(5, produto.getPreco());
            comandoComConexao.setDouble(6, produto.getQuantidade());
            comandoComConexao.setDouble(7, produto.getEstoque_min());
            comandoComConexao.setObject(8, produto.getDtcreate());
            comandoComConexao.setBoolean(9, produto.isLativo());

            comandoComConexao.executeUpdate();

            ResultSet resultadoOperacao = comandoComConexao.getGeneratedKeys();
            resultadoOperacao.next();

            produto.setId(resultadoOperacao.getLong("id"));

            comandoComConexao.close();
            resultadoOperacao.close();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return produto;
    }

    private Produto buscarProduto(Object parametroDeBusca, String sql) {
        PreparedStatement comandoSqlComConexao;
        try {
            comandoSqlComConexao = conexao.prepareStatement(sql);
            comandoSqlComConexao.setObject(1, parametroDeBusca);
            ResultSet resultadoOperacao = comandoSqlComConexao.executeQuery();

            Produto produto = null;
            if (resultadoOperacao.next()) {
                produto = new Produto(
                        resultadoOperacao.getLong("id"),
                        UUID.fromString(resultadoOperacao.getString("hash")),
                        resultadoOperacao.getString("nome"),
                        resultadoOperacao.getString("descricao"),
                        resultadoOperacao.getString("ean13"),
                        resultadoOperacao.getDouble("preco"),
                        resultadoOperacao.getDouble("quantidade"),
                        resultadoOperacao.getDouble("estoque_min"),
                        resultadoOperacao.getTimestamp("dtcreate"),
                        resultadoOperacao.getTimestamp("dtupdate"),
                        resultadoOperacao.getBoolean("lativo")
                );
            }
            comandoSqlComConexao.close();
            resultadoOperacao.close();
            return produto;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Produto> buscarTodos() {
        String selectAllSql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<Produto>();

        try {
            PreparedStatement comandoSqlComConexao = conexao.prepareStatement(selectAllSql);
            ResultSet resultadoOperacao = comandoSqlComConexao.executeQuery();

            while (resultadoOperacao.next()) {
                Produto produto = new Produto(
                        resultadoOperacao.getLong("id"),
                        UUID.fromString(resultadoOperacao.getString("hash")),
                        resultadoOperacao.getString("nome"),
                        resultadoOperacao.getString("descricao"),
                        resultadoOperacao.getString("ean13"),
                        resultadoOperacao.getDouble("preco"),
                        resultadoOperacao.getDouble("quantidade"),
                        resultadoOperacao.getDouble("estoque_min"),
                        resultadoOperacao.getTimestamp("dtcreate"),
                        resultadoOperacao.getTimestamp("dtupdate"),
                        resultadoOperacao.getBoolean("lativo")
                );

                produtos.add(produto);
            }

            comandoSqlComConexao.close();
            resultadoOperacao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return produtos;
    }

    public List<Produto> buscarTodosAtivos() {
        String selectAllSql = "SELECT * FROM produto WHERE lativo = true";
        List<Produto> produtos = new ArrayList<Produto>();

        try {
            PreparedStatement comandoSqlComConexao = conexao.prepareStatement(selectAllSql);
            ResultSet resultadoOperacao = comandoSqlComConexao.executeQuery();

            while (resultadoOperacao.next()) {
                Produto produto = new Produto(
                        resultadoOperacao.getLong("id"),
                        UUID.fromString(resultadoOperacao.getString("hash")),
                        resultadoOperacao.getString("nome"),
                        resultadoOperacao.getString("descricao"),
                        resultadoOperacao.getString("ean13"),
                        resultadoOperacao.getDouble("preco"),
                        resultadoOperacao.getDouble("quantidade"),
                        resultadoOperacao.getDouble("estoque_min"),
                        resultadoOperacao.getTimestamp("dtcreate"),
                        resultadoOperacao.getTimestamp("dtupdate"),
                        resultadoOperacao.getBoolean("lativo")
                );

                produtos.add(produto);
            }

            comandoSqlComConexao.close();
            resultadoOperacao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return produtos;
    }

    public List<Produto> buscarTodosInativos() {
        String selectAllSql = "SELECT * FROM produto WHERE lativo = false";
        List<Produto> produtos = new ArrayList<Produto>();

        try {
            PreparedStatement comandoSqlComConexao = conexao.prepareStatement(selectAllSql);
            ResultSet resultadoOperacao = comandoSqlComConexao.executeQuery();

            while (resultadoOperacao.next()) {
                Produto produto = new Produto(
                        resultadoOperacao.getLong("id"),
                        UUID.fromString(resultadoOperacao.getString("hash")),
                        resultadoOperacao.getString("nome"),
                        resultadoOperacao.getString("descricao"),
                        resultadoOperacao.getString("ean13"),
                        resultadoOperacao.getDouble("preco"),
                        resultadoOperacao.getDouble("quantidade"),
                        resultadoOperacao.getDouble("estoque_min"),
                        resultadoOperacao.getTimestamp("dtcreate"),
                        resultadoOperacao.getTimestamp("dtupdate"),
                        resultadoOperacao.getBoolean("lativo")
                );

                produtos.add(produto);
            }

            comandoSqlComConexao.close();
            resultadoOperacao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return produtos;
    }


    public Produto buscarPorHash(UUID hash) {
        String selectSql = "SELECT * FROM produto WHERE hash = ?";
        return buscarProduto(hash, selectSql);
    }

    public Produto buscarPorHashAtivo(UUID hash) {
        String selectSql = "SELECT * FROM produto WHERE hash = ? AND lativo = true";
        return buscarProduto(hash, selectSql);
    }

    public boolean existeHash(UUID hash){
        String selectSql = "SELECT * FROM produto WHERE hash = ?";

        return buscarProduto(hash, selectSql) != null;
    }

    public boolean existeNome(String nome){
        String selectSql = "SELECT * FROM produto WHERE nome = ?";

        return buscarProduto(nome, selectSql) != null;
    }

    public boolean existeEan13(String ean13){
        String selectSql = "SELECT * FROM produto WHERE ean13 = ?";

        return buscarProduto(ean13, selectSql) != null;
    }

    public Produto atualizarProduto(UUID hash, Produto produto) {
        try {
            String updateSql = "UPDATE produto SET descricao = ?, preco = ?, quantidade = ?, estoque_min = ?, dtupdate = ? WHERE hash = ?";

            PreparedStatement comandoComConexao = conexao.prepareStatement(updateSql);

            comandoComConexao.setString(1, produto.getDescricao());
            comandoComConexao.setDouble(2, produto.getPreco());
            comandoComConexao.setDouble(3, produto.getQuantidade());
            comandoComConexao.setDouble(4, produto.getEstoque_min());
            comandoComConexao.setTimestamp(5, produto.getDtupdate());
            comandoComConexao.setObject(6, hash);

            comandoComConexao.executeUpdate();
            comandoComConexao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return produto;
    }

    public void alterarLativo(UUID hash, boolean lativo){
        String sql = "UPDATE produto SET lativo = ?, dtupdate = CURRENT_TIMESTAMP WHERE hash = ?";
        try {
            PreparedStatement comandoComConexao = conexao.prepareStatement(sql);
            comandoComConexao.setBoolean(1, lativo);
            comandoComConexao.setObject(2, hash);

            comandoComConexao.executeUpdate();
            comandoComConexao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void alterarEstoque(Produto produto){
        String sql = "UPDATE produto SET quantidade = ?, dtupdate = ? WHERE hash = ?";
        try {
            PreparedStatement comandoComConexao = conexao.prepareStatement(sql);
            comandoComConexao.setDouble(1, produto.getQuantidade());
            comandoComConexao.setObject(2, produto.getDtupdate());
            comandoComConexao.setObject(3, produto.getHash());

            comandoComConexao.executeUpdate();
            comandoComConexao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String excluirProduto(UUID hash) {
        try {
            String deleteSql = "DELETE FROM produto WHERE hash = ?";

            PreparedStatement comandoComConexao = conexao.prepareStatement(deleteSql);

            comandoComConexao.setObject(1, hash);

            comandoComConexao.executeUpdate();
            comandoComConexao.close();
            return "Produto excluido com sucesso.";
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
