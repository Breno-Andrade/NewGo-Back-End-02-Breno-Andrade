package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoAtualizacaoDto;
import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.excecao.ProdutoAtualizacaoExcecao;
import dominio.produto.excecao.ProdutoInsercaoExcecao;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;
import infraestrutura.produto.entidade.Produto;

import java.sql.Timestamp;
import java.util.UUID;

public class ProdutoAtualizacaoServico {
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper produtoMapper = new ProdutoMapper();

    public ProdutoRetornoDto atualizarProduto(boolean alterarLativo, UUID hash, ProdutoAtualizacaoDto produtoDto){
        Produto produtoTemp = produtoDAO.buscarPorHash(hash);

        if(!alterarLativo){
            verificarLativo(produtoTemp);
        }
        verificarModificacoesInvalidas(produtoDto, produtoTemp);

        Produto produto = produtoMapper.atualizacaoDtoParaEntidade(
                produtoTemp.getId(), produtoTemp.getHash(),
                produtoTemp.getNome(), produtoDto.getDescricao(),
                produtoTemp.getEan13(), produtoDto.getPreco(),
                produtoDto.getQuantidade(), produtoDto.getEstoque_min(),
                produtoTemp.getDtcreate(), produtoTemp.getDtupdate(),
                produtoTemp.isLativo());
        produto.setLativo(true);
        produtoDAO.atualizarProduto(hash, produto);

        return produtoMapper.entidadeParaRetornoDto(produto);
    }

    public void verificarModificacoesInvalidas(ProdutoAtualizacaoDto produtoDto, Produto produto){
        verificarId(produtoDto, produto);
        verificarHash(produtoDto, produto);
        verificarNome(produtoDto, produto);
        verificarEan13(produtoDto, produto);
        verificarDtcreate(produtoDto, produto);
        verificarDtupdate(produtoDto, produto);
    }

    public void verificarId(ProdutoAtualizacaoDto produtoDto, Produto produto){
        if (produtoDto.getId() != 0 && produtoDto.getId() != produto.getId()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.ID_INVALIDO);
        }
    }

    public void verificarHash(ProdutoAtualizacaoDto produtoDto, Produto produto){
        if (produtoDto.getHash() != null && produtoDto.getHash() != produto.getHash()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.HASH_INVALIDO);
        }
    }

    public void verificarNome(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getNome() != null && !produtoDto.getNome().equals(produto.getNome())){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.NOME_INVALIDO);
        }
    }

    public void verificarEan13(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getEan13() != null && !produtoDto.getEan13().equals(produto.getEan13())){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.EAN13_INVALIDO);
        }
    }

    public void verificarDtcreate(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getDtcreate() != null && produtoDto.getDtcreate() != produto.getDtcreate()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.DTCREATE_INVALIDO);
        }
    }

    public void verificarDtupdate(ProdutoAtualizacaoDto produtoDto , Produto produto){
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
