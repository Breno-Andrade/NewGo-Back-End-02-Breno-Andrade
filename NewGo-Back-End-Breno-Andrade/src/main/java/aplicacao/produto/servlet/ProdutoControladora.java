package aplicacao.produto.servlet;

import aplicacao.produto.dto.ProdutoAtualizacaoDto;
import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRequisicaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import com.google.gson.Gson;
import dominio.produto.excecao.ErroJson;
import dominio.produto.servico.ProdutoAtualizacaoServico;
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
public class ProdutoControladora extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    ProdutoDAO produtoDAO = new ProdutoDAO();

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
            resp.setStatus(200);
            printer.flush();
        } catch (RuntimeException e){
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

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
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        UUID produtoHash = UUID.fromString(req.getPathInfo()
                .replaceAll("\\?.*&.*", "")
                .replace("/", ""));

        boolean alterarLativo = Boolean.parseBoolean(req.getParameter("ativar"));
        StringBuffer stringBuffer = new StringBuffer();

        BufferedReader bufferedReader = req.getReader();
        String atributos;

        while((atributos = bufferedReader.readLine()) != null) {
            stringBuffer.append(atributos);
        }

        ProdutoAtualizacaoDto produtoDto = gson.fromJson(stringBuffer.toString(), ProdutoAtualizacaoDto.class);

        PrintWriter printWriter = resp.getWriter();
        ProdutoAtualizacaoServico produtoServico = new ProdutoAtualizacaoServico();
        printWriter.print(gson.toJson(produtoServico.atualizarProduto(alterarLativo, produtoHash, produtoDto)));
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setStatus(202);

        UUID produtoHash = UUID.fromString(req.getPathInfo().replaceAll("/", ""));

        BufferedReader bufferedReader = req.getReader();
        String prodResp = produtoDAO.excluirProduto(produtoHash);

        PrintWriter printWriter = resp.getWriter();

        if(prodResp == null) {
            resp.setStatus(404);
            printWriter.print("Produto não foi encontrado.");
            printWriter.flush();
        } else {
            printWriter.print(prodResp);
            printWriter.flush();
        }
    }
}
