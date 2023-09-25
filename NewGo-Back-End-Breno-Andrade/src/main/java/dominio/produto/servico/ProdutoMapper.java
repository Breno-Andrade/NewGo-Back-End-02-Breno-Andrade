package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import infraestrutura.produto.entidade.Produto;

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
