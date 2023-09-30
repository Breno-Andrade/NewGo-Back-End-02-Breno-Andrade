package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoRequisicaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import infraestrutura.produto.dao.ProdutoDAO;
import infraestrutura.produto.entidade.Produto;

public class ProdutoExclusaoServico {
    private ProdutoMapper produtoMapper = new ProdutoMapper();
    private UtilProduto utilProduto = new UtilProduto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public ProdutoRetornoDto excluirProduto(ProdutoRequisicaoDto produtoRequisicaoDto){
        utilProduto.verificarHash(produtoRequisicaoDto.getHash());
        Produto produto = produtoDAO.buscarPorHash(produtoRequisicaoDto.getHash());

        produtoDAO.excluirProduto(produtoRequisicaoDto.getHash());

        return produtoMapper.entidadeParaRetornoDto(produto);
    }
}
