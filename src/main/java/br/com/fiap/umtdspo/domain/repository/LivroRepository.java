package br.com.fiap.umtdspo.domain.repository;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.domain.model.Livro;

import java.util.List;

public interface LivroRepository {
    Livro buscarPorId(Integer id) throws EntidadeNaoLocalizadaException;
    Livro buscarPorIsbn(String isbn) throws EntidadeNaoLocalizadaException;
    List<Livro> buscarTodos();
    List<Livro> buscarPorAutor(Integer autorId);
    Livro salvar(Livro livro);
    Livro editar(Livro livro) throws EntidadeNaoLocalizadaException;
    void remover(Integer id) throws EntidadeNaoLocalizadaException;
}
