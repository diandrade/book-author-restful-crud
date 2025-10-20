package br.com.fiap.umtdspo.domain.repository;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.domain.model.Autor;

import java.util.List;

public interface AutorRepository {
    Autor buscarPorId(Integer id) throws EntidadeNaoLocalizadaException;
    Autor buscarPorEmail(String email) throws EntidadeNaoLocalizadaException;
    List<Autor> buscarTodos();
    Autor salvar(Autor autor);
    Autor editar (Autor autor);
    void remover(Integer id);

}
