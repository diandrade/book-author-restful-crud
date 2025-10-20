package br.com.fiap.umtdspo.infrastructure.api.rest;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.interfaces.controller.AutorController;
import br.com.fiap.umtdspo.interfaces.controller.LivroController;
import br.com.fiap.umtdspo.interfaces.dto.AutorRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.AutorResponseDTO;
import br.com.fiap.umtdspo.interfaces.dto.LivroResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutorRestController {
    private final AutorController autorController;
    private final LivroController livroController;

    @Inject
    public AutorRestController(AutorController autorController, LivroController livroController) { // ← ADICIONAR parâmetro
        this.autorController = autorController;
        this.livroController = livroController;
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            AutorResponseDTO autor = autorController.getAutorById(id);
            return Response.ok(autor).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/books")
    public Response buscarLivrosPorAutor(@PathParam("id") Integer autorId) {
        try {
            List<LivroResponseDTO> livros = livroController.listarLivrosPorAutor(autorId); // ← Agora funciona!
            return Response.ok(livros).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodosAutores() {
        try {
            List<AutorResponseDTO> autores = autorController.getAllAutores();
            return Response.ok(autores).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarAutor(AutorRequestDTO autorRequest) {
        try {
            AutorResponseDTO autor = autorController.criarAutor(autorRequest);
            return Response.status(Response.Status.CREATED).entity(autor).build();

        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarAutor(@PathParam("id") Integer id, AutorRequestDTO authorRequest) {
        try {
            AutorResponseDTO autor = autorController.editarAutor(id, authorRequest);
            return Response.status(Response.Status.OK).entity(autor).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAutor(@PathParam("id") Integer id) {
        try {
            autorController.deleteAutor(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}