package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoRequisicaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.Util.UtilVerificacoesProduto;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import dominio.produto.excecao.ProdutoRequisicaoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;
import infraestrutura.produto.entidade.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRequisicaoServico {
    private UtilVerificacoesProduto utilVerificacoesProduto = new UtilVerificacoesProduto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper produtoMapper = new ProdutoMapper();

    public ProdutoRetornoDto requisitarProduto(ProdutoRequisicaoDto produtoDto) {
        utilVerificacoesProduto.verificarHash(produtoDto.getHash());

        return produtoMapper.entidadeParaRetornoDto(produtoDAO.buscarPorHash(produtoDto.getHash()));
    }

    public ProdutoRetornoDto requisitarProdutoAtivo(ProdutoRequisicaoDto produtoDto) {

        try{
            utilVerificacoesProduto.verificarHash(produtoDto.getHash());
            if(produtoDAO.buscarPorHashAtivo(produtoDto.getHash()) == null) {
                throw new ProdutoInvalidoExcecao(ProdutoRequisicaoExcecao.PRODUTO_INATIVO);
            }
            return produtoMapper.entidadeParaRetornoDto(produtoDAO.buscarPorHashAtivo(produtoDto.getHash()));
        } catch (IllegalArgumentException e){
            throw new ProdutoInvalidoExcecao(e.getMessage());
        }
    }

    public List<ProdutoRetornoDto> requisitarTodosProdutos() {
        return produtoMapper.listaEntidadeParaListaRetorno(produtoDAO.buscarTodos());
    }
    public List<ProdutoRetornoDto> requisitarTodosProdutosAtivos() {
        return produtoMapper.listaEntidadeParaListaRetorno(produtoDAO.buscarTodosAtivos());
    }
    public List<ProdutoRetornoDto> requisitarTodosProdutosInativos() {
        return produtoMapper.listaEntidadeParaListaRetorno(produtoDAO.buscarTodosInativos());
    }

    public List<ProdutoRetornoDto> requisitarProdutosEstoqueMenorMinimo(){
        List<Produto> produtosTemp = produtoDAO.buscarTodosAtivos();
        List<Produto> produtosEstoqueNegativo = new ArrayList<>();
        for (Produto produto : produtosTemp){
            if (produto.getQuantidade() < produto.getEstoque_min()){
                produtosEstoqueNegativo.add(produto);
            }
        }
        return produtoMapper.listaEntidadeParaListaRetorno(produtosEstoqueNegativo);
    }
}
