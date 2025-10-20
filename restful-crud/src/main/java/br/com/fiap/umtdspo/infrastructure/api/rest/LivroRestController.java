package br.com.fiap.umtdspo.infrastructure.api.rest;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.interfaces.controller.LivroController;
import br.com.fiap.umtdspo.interfaces.dto.LivroRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.LivroResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LivroRestController {
    private final LivroController livroController;

    @Inject
    public LivroRestController(LivroController livroController) {
        this.livroController = livroController;
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            LivroResponseDTO livro = livroController.buscarLivroPorId(id);
            return Response.ok(livro).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodosLivros() {
        try {
            List<LivroResponseDTO> livros = livroController.listarLivros();
            return Response.ok(livros).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/author/{authorId}")
    public Response buscarLivrosPorAutor(@PathParam("authorId") Integer autorId) {
        try {
            List<LivroResponseDTO> livros = livroController.listarLivrosPorAutor(autorId);
            return Response.ok(livros).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarLivro(LivroRequestDTO livroRequest) {
        try {
            LivroResponseDTO livro = livroController.criarLivro(livroRequest);
            return Response.status(Response.Status.CREATED).entity(livro).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarLivro(@PathParam("id") Integer id, LivroRequestDTO livroRequest) {
        try {
            LivroResponseDTO livro = livroController.editarLivro(id, livroRequest);
            return Response.status(Response.Status.OK).entity(livro).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarLivro(@PathParam("id") Integer id) {
        try {
            livroController.removerLivro(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}