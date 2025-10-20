package br.com.fiap.umtdspo.interfaces.controller;

import br.com.fiap.umtdspo.domain.model.Livro;
import br.com.fiap.umtdspo.domain.service.LivroService;
import br.com.fiap.umtdspo.interfaces.dto.LivroRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.LivroResponseDTO;
import br.com.fiap.umtdspo.interfaces.mapper.LivroMapper;
import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import java.util.List;

@ApplicationScoped
public class LivroControllerImpl implements LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @Inject
    public LivroControllerImpl(LivroService livroService, LivroMapper livroMapper) {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
    }

    @POST
    @Override
    public LivroResponseDTO criarLivro(LivroRequestDTO livroRequest) {
        Livro livro = livroMapper.toDomain(livroRequest);
        Livro livroCriado = this.livroService.criar(livro);
        return livroMapper.toResponseDTO(livroCriado);
    }

    @PUT
    @Path("/{id}")
    @Override
    public LivroResponseDTO editarLivro(@PathParam("id") Integer id, LivroRequestDTO livroRequest) throws EntidadeNaoLocalizadaException {
        Livro livro = livroMapper.toDomain(livroRequest);
        Livro livroAtualizado = this.livroService.editar(id, livro);
        return livroMapper.toResponseDTO(livroAtualizado);
    }

    @DELETE
    @Path("/{id}")
    @Override
    public void removerLivro(@PathParam("id") Integer id) throws EntidadeNaoLocalizadaException {
        this.livroService.remover(id);
    }

    @GET
    @Path("/{id}")
    @Override
    public LivroResponseDTO buscarLivroPorId(@PathParam("id") Integer id) throws EntidadeNaoLocalizadaException {
        Livro livro = this.livroService.localizar(id);
        return livroMapper.toResponseDTO(livro);
    }

    @GET
    @Override
    public List<LivroResponseDTO> listarLivros() {
        List<Livro> livros = livroService.listarTodos();
        return livroMapper.toResponseDTOList(livros);
    }

    @GET
    @Path("/autor/{autorId}")
    @Override
    public List<LivroResponseDTO> listarLivrosPorAutor(@PathParam("autorId") Integer autorId) {
        List<Livro> livros = livroService.listarPorAutor(autorId);
        return livroMapper.toResponseDTOList(livros);
    }
}