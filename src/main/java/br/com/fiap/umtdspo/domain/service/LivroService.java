package br.com.fiap.umtdspo.domain.service;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.domain.model.Livro;

import java.util.List;

public interface LivroService {
    Livro criar(Livro livro);
    Livro editar(Integer id, Livro livro) throws EntidadeNaoLocalizadaException;
    Livro remover(Integer id) throws EntidadeNaoLocalizadaException;
    Livro localizar(Integer id) throws EntidadeNaoLocalizadaException;
    List<Livro> listarTodos();
    List<Livro> listarPorAutor(Integer autorId);
}
