package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoAtualizacaoDto;
import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import infraestrutura.produto.entidade.Produto;

import java.sql.Timestamp;
import java.util.UUID;

public class ProdutoMapper {
    public Produto insercaoDtoParaEntidade(ProdutoInsercaoDto produtoDto) {
        return new Produto(
                produtoDto.getNome(),
                produtoDto.getDescricao(),
                produtoDto.getEan13(),
                produtoDto.getPreco(),
                produtoDto.getQuantidade(),
                produtoDto.getEstoque_min()
        );
    }

    public Produto atualizacaoDtoParaEntidade(ProdutoAtualizacaoDto produtoDto) {
        return new Produto(
                produtoDto.getDescricao(),
                produtoDto.getPreco(),
                produtoDto.getQuantidade(),
                produtoDto.getEstoque_min()
        );
    }

    public Produto atualizacaoDtoParaEntidade(long id, UUID hash, String nome, String descricao, String ean13, double preco, double quantidade, double estoque_min, Timestamp dtcreate, Timestamp dtupdate, boolean lativo) {
        return new Produto(
                id,
                hash,
                nome,
                descricao,
                ean13,
                preco,
                quantidade,
                estoque_min,
                dtcreate,
                dtupdate,
                lativo
        );
    }
    public ProdutoRetornoDto entidadeParaRetornoDto(Produto produto){
        return new ProdutoRetornoDto(
                produto.getId(),
                produto.getHash(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getEan13(),
                produto.getPreco(),
                produto.getQuantidade(),
                produto.getEstoque_min(),
                produto.getDtcreate(),
                produto.getDtupdate(),
                produto.isLativo()
        );
    }
}
