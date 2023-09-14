package dados;

import dominio.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdutoDAO {
    private GeradorConexao gerador = new GeradorConexao();
    private Connection conexao = gerador.gerarConexao();

    public Produto inserirNovoProduto(Produto produto) {
        try {
            String insertSql = "INSERT INTO produto (nome, descricao, ean13, preco, quantidade, estoque_min)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement comandoComConexao = conexao.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            comandoComConexao.setString(1, produto.getNome());
            comandoComConexao.setString(2, produto.getDescricao());
            comandoComConexao.setString(3, produto.getEan13());
            comandoComConexao.setDouble(4, produto.getPreco());
            comandoComConexao.setDouble(5, produto.getQuantidade());
            comandoComConexao.setDouble(6, produto.getEstoque_min());

            comandoComConexao.executeUpdate();

            ResultSet resultadoOperacao = comandoComConexao.getGeneratedKeys();
            resultadoOperacao.next();

            Long idGerado = resultadoOperacao.getLong("id");
            produto.setId(idGerado);
            String hashGerado = resultadoOperacao.getString("hash");
            produto.setHash(UUID.fromString(hashGerado));

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


    public Produto buscarPorHash(UUID hash) {
        String selectSql = "SELECT * FROM produto WHERE hash = ?";
        return buscarProduto(hash, selectSql);
    }

    public Produto atualizarProduto(UUID hash, Produto produto) {
        try {
            String updateSql = "UPDATE produto SET nome = ?, descricao = ?, ean13 = ?, preco = ?, quantidade = ?, estoque_min = ?, dtupdate = CURRENT_TIMESTAMP WHERE hash = ?";

            PreparedStatement comandoComConexao = conexao.prepareStatement(updateSql);

            comandoComConexao.setString(1, produto.getNome());
            comandoComConexao.setString(2, produto.getDescricao());
            comandoComConexao.setString(3, produto.getEan13());
            comandoComConexao.setDouble(4, produto.getPreco());
            comandoComConexao.setDouble(5, produto.getQuantidade());
            comandoComConexao.setDouble(6, produto.getEstoque_min());
            comandoComConexao.setObject(7, hash);

            int linhasAfetadas = comandoComConexao.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhum registro atualizado. Verifique o ID do produto.");
            }


            comandoComConexao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return produto;
    }

    public String excluirProduto(UUID hash) {
        try {
            String deleteSql = "DELETE FROM produto WHERE hash = ?";

            PreparedStatement comandoComConexao = conexao.prepareStatement(deleteSql);

            comandoComConexao.setObject(1, hash);

            int linhasAfetadas = comandoComConexao.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhum registro excluído. Verifique o ID do produto.");
            }
            comandoComConexao.close();
            return "Produto excluido com sucesso.";
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
