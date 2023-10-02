package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoRequisicaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.Util.UtilVerificacoesProduto;
import infraestrutura.produto.dao.ProdutoDAO;

public class ProdutoRequisicaoServico{
    private UtilVerificacoesProduto utilVerificacoesProduto = new UtilVerificacoesProduto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper produtoMapper = new ProdutoMapper();

    public ProdutoRetornoDto requisitarProduto(ProdutoRequisicaoDto produtoDto){
        utilVerificacoesProduto.verificarHash(produtoDto.getHash());

        return produtoMapper.entidadeParaRetornoDto(produtoDAO.buscarPorHash(produtoDto.getHash()));
    }



}
