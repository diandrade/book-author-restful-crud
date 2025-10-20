package br.com.fiap.umtdspo.domain.service;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.domain.model.Autor;

import java.util.List;

public interface AutorService {
    Autor criar(Autor autor);
    Autor editar(Integer id, Autor autor) throws EntidadeNaoLocalizadaException;
    Autor remover(Integer id) throws EntidadeNaoLocalizadaException;
    Autor localizar(Integer id) throws EntidadeNaoLocalizadaException;
    List<Autor> listarTodos();
}
