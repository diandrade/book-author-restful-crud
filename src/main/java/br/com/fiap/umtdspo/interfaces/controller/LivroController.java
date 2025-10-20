package br.com.fiap.umtdspo.interfaces.controller;

import br.com.fiap.umtdspo.interfaces.dto.LivroRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.LivroResponseDTO;
import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.ws.rs.PathParam;
import java.util.List;

public interface LivroController {
    LivroResponseDTO criarLivro(LivroRequestDTO livroRequest);
    LivroResponseDTO editarLivro(@PathParam("id") Integer id, LivroRequestDTO livroRequest) throws EntidadeNaoLocalizadaException;
    void removerLivro(@PathParam("id") Integer id) throws EntidadeNaoLocalizadaException;
    LivroResponseDTO buscarLivroPorId(@PathParam("id") Integer id) throws EntidadeNaoLocalizadaException;
    List<LivroResponseDTO> listarLivros();
    List<LivroResponseDTO> listarLivrosPorAutor(@PathParam("autorId") Integer autorId);
}