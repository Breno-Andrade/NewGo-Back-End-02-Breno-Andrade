package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoRequisicaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.Util.UtilVerificacoesProduto;
import infraestrutura.produto.dao.ProdutoDAO;
import infraestrutura.produto.entidade.Produto;

public class ProdutoExclusaoServico {
    private ProdutoMapper produtoMapper = new ProdutoMapper();
    private UtilVerificacoesProduto utilVerificacoesProduto = new UtilVerificacoesProduto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public ProdutoRetornoDto excluirProduto(ProdutoRequisicaoDto produtoRequisicaoDto){
        utilVerificacoesProduto.verificarHash(produtoRequisicaoDto.getHash());
        Produto produto = produtoDAO.buscarPorHash(produtoRequisicaoDto.getHash());

        produtoDAO.excluirProduto(produtoRequisicaoDto.getHash());

        return produtoMapper.entidadeParaRetornoDto(produto);
    }
}
