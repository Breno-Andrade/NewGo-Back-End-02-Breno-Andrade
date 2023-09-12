package dados;

import dominio.Produto;

import java.sql.*;
import java.util.UUID;

public class ProdutoDAO {
    private GeradorConexao gerador = new GeradorConexao();
    private Connection conexao = gerador.gerarConexao();
    public void inserirNovoProduto(Produto produto) {
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

            Long generatedId = resultadoOperacao.getLong("id");
            produto.setId(generatedId);

            comandoComConexao.close();
            resultadoOperacao.close();

        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Produto buscarProduto(String parametroDeBusca, String sql) {
        PreparedStatement comandoSqlComConexao;
        try {
            comandoSqlComConexao = conexao.prepareStatement(sql);
            comandoSqlComConexao.setString(1, parametroDeBusca);
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

    public Produto buscarPorNome(String nomeDoProduto) {
        String selectSql = "SELECT * FROM produto WHERE LOWER(nome) = LOWER(?)";
        return buscarProduto(nomeDoProduto, selectSql);
    }

    public void atualizarProduto(Long id, Produto produto) {
        try {
            String updateSql = "UPDATE produto SET nome = ?, descricao = ?, ean13 = ?, preco = ?, quantidade = ?, estoque_min = ?, dtupdate = CURRENT_TIMESTAMP WHERE id = ?";

            PreparedStatement comandoComConexao = conexao.prepareStatement(updateSql);

            comandoComConexao.setString(1, produto.getNome());
            comandoComConexao.setString(2, produto.getDescricao());
            comandoComConexao.setString(3, produto.getEan13());
            comandoComConexao.setDouble(4, produto.getPreco());
            comandoComConexao.setDouble(5, produto.getQuantidade());
            comandoComConexao.setDouble(6, produto.getEstoque_min());
            comandoComConexao.setLong(7, id); // ID do produto a ser atualizado

            int linhasAfetadas = comandoComConexao.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhum registro atualizado. Verifique o ID do produto.");
            }

            comandoComConexao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void excluirProduto(long idDoProduto) {
        try {
            String deleteSql = "DELETE FROM produto WHERE id = ?";

            PreparedStatement comandoComConexao = conexao.prepareStatement(deleteSql);

            comandoComConexao.setLong(1, idDoProduto); // ID do produto a ser excluído

            int linhasAfetadas = comandoComConexao.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhum registro excluído. Verifique o ID do produto.");
            }

            comandoComConexao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
