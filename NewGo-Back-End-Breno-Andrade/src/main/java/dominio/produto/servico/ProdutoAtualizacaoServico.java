package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoAtualizacaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.excecao.ProdutoAtualizacaoExcecao;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;
import infraestrutura.produto.entidade.Produto;

import java.util.UUID;

public class ProdutoAtualizacaoServico {
    private UtilProduto utilProduto = new UtilProduto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper produtoMapper = new ProdutoMapper();

    public ProdutoRetornoDto atualizarProduto(boolean alterarLativo, UUID hash, ProdutoAtualizacaoDto produtoDto){
        utilProduto.verificarHash(hash);
        Produto produtoTemp = produtoDAO.buscarPorHash(hash);

        if(!alterarLativo){
            verificarLativo(produtoTemp);
        }
        verificacaoModificacoesInvalidas(produtoDto, produtoTemp);

        Produto produto = produtoMapper.atualizacaoDtoParaEntidade(produtoDto, produtoTemp);
        produto.setDtupdate(utilProduto.gerarTimestampAtual());
        produtoDAO.alterarLativo(hash, true);

//
//        produto = produtoDAO.buscarPorHash(hash);
        return produtoMapper.entidadeParaRetornoDto(produtoDAO.atualizarProduto(hash, produto));
    }

    public void verificacaoModificacoesInvalidas(ProdutoAtualizacaoDto produtoDto, Produto produto){
        utilProduto.precoNegativo(produtoDto.getPreco());
        utilProduto.quantidadeNegativo(produtoDto.getQuantidade());
        utilProduto.estoqueMinNegativo(produtoDto.getEstoque_min());
        verificarAlteracaoId(produtoDto, produto);
        verificarAlteracaoHash(produtoDto, produto);
        verificarAlteracaoNome(produtoDto, produto);
        verificarAlteracaoEan13(produtoDto, produto);
        verificarAlteracaoDtcreate(produtoDto, produto);
        verificarAlteracaoDtupdate(produtoDto, produto);
    }

    public void verificarAlteracaoId(ProdutoAtualizacaoDto produtoDto, Produto produto){
        if (produtoDto.getId() != 0 && produtoDto.getId() != produto.getId()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.ID_INVALIDO);
        }
    }

    public void verificarAlteracaoHash(ProdutoAtualizacaoDto produtoDto, Produto produto){
        if (produtoDto.getHash() != null && produtoDto.getHash() != produto.getHash()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.HASH_INVALIDO);
        }
    }

    public void verificarAlteracaoNome(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getNome() != null && !produtoDto.getNome().equals(produto.getNome())){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.NOME_INVALIDO);
        }
    }

    public void verificarAlteracaoEan13(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getEan13() != null && !produtoDto.getEan13().equals(produto.getEan13())){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.EAN13_INVALIDO);
        }
    }

    public void verificarAlteracaoDtcreate(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getDtcreate() != null && produtoDto.getDtcreate() != produto.getDtcreate()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.DTCREATE_INVALIDO);
        }
    }

    public void verificarAlteracaoDtupdate(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getDtupdate() != null && produtoDto.getDtupdate() != produto.getDtupdate()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.DTUPDATE_INVALIDO);
        }
    }

    private void verificarLativo(Produto produto){
        if(!produto.isLativo()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.LATIVO_INVALIDO);
        }
    }
}
