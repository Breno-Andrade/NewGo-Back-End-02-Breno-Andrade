package aplicacao.produto.servlet;

import aplicacao.produto.dto.ProdutoAtualizacaoDto;
import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRequisicaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import com.google.gson.Gson;
import dominio.produto.excecao.ErroJson;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import dominio.produto.servico.ProdutoAtualizacaoServico;
import dominio.produto.servico.ProdutoExclusaoServico;
import dominio.produto.servico.ProdutoInsercaoServico;
import dominio.produto.servico.ProdutoRequisicaoServico;
import infraestrutura.produto.dao.ProdutoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;


@WebServlet("/produtos/*")
public class ProdutoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        try {
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = req.getReader();
            String atributos;

            while((atributos = bufferedReader.readLine()) != null) {
                stringBuffer.append(atributos);
            }
            ProdutoInsercaoDto produtoDto = gson.fromJson(stringBuffer.toString(), ProdutoInsercaoDto.class);
            ProdutoInsercaoServico produtoInsercaoServico = new ProdutoInsercaoServico();
            ProdutoRetornoDto produtoRetornoDto = produtoInsercaoServico.salvarNovoProduto(produtoDto);

            PrintWriter printWriter = resp.getWriter();
            printWriter.print(gson.toJson(produtoRetornoDto));
            printWriter.flush();
            resp.setStatus(201);
        } catch (ProdutoInvalidoExcecao e){
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        try{
            ProdutoRequisicaoDto produtoDto = new ProdutoRequisicaoDto(
                    UUID.fromString(req.getPathInfo().replaceAll("/", ""))
            );

            ProdutoRequisicaoServico produtoServico = new ProdutoRequisicaoServico();
            PrintWriter printer = resp.getWriter();
            printer.print(gson.toJson(produtoServico.requisitarProduto(produtoDto)));
            printer.flush();
            resp.setStatus(200);
        } catch (ProdutoInvalidoExcecao e){
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        try {
        BufferedReader bufferedReader = req.getReader();
        StringBuffer stringBuffer = new StringBuffer();
        String atributos;
        while((atributos = bufferedReader.readLine()) != null) {
            stringBuffer.append(atributos);
        }

        ProdutoAtualizacaoDto produtoDto = gson.fromJson(stringBuffer.toString(), ProdutoAtualizacaoDto.class);

        String urlRequisicao = req.getRequestURI();
        String[] partesDaURL = urlRequisicao.split("/");

        UUID produtoHash = null;
        if (partesDaURL.length >= 4){
            produtoHash = UUID.fromString(partesDaURL[3].replace("/", ""));
        }

        boolean alterarLativo = false;
        if (partesDaURL.length == 5){
             alterarLativo = partesDaURL[4].equalsIgnoreCase("ativar");
        }

        PrintWriter printWriter = resp.getWriter();
        ProdutoAtualizacaoServico produtoServico = new ProdutoAtualizacaoServico();
        printWriter.print(gson.toJson(produtoServico.atualizarProduto(alterarLativo, produtoHash, produtoDto)));
        printWriter.flush();
        resp.setStatus(202);
        } catch(ProdutoInvalidoExcecao e){
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        try {
            ProdutoRequisicaoDto produtoDto = new ProdutoRequisicaoDto(
                    UUID.fromString(req.getPathInfo().replaceAll("/", ""))
            );

            ProdutoExclusaoServico produtoServico = new ProdutoExclusaoServico();
            produtoServico.excluirProduto(produtoDto);

            resp.setStatus(202);
        } catch (ProdutoInvalidoExcecao e) {
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(404);
        }
    }
}
