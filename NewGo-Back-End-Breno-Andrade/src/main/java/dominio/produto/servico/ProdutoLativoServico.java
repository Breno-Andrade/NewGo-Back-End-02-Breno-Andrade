package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoLativoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.Util.UtilVerificacoesProduto;
import infraestrutura.produto.dao.ProdutoDAO;
import infraestrutura.produto.entidade.Produto;

import java.util.UUID;

public class ProdutoLativoServico {
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper produtoMapper = new ProdutoMapper();
    private UtilVerificacoesProduto utilVerificacoesProduto = new UtilVerificacoesProduto();

    public ProdutoRetornoDto alterarLativo(UUID hash, ProdutoLativoDto produtoDto){
        utilVerificacoesProduto.verificarHash(hash);
        produtoDAO.alterarLativo(hash, produtoDto.isLativo());
        return produtoMapper.entidadeParaRetornoDto(produtoDAO.buscarPorHash(hash));
    }
}
