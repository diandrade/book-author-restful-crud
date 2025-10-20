// application/service/LivroServiceImpl.java
package br.com.fiap.umtdspo.application.service;

import br.com.fiap.umtdspo.domain.model.Livro;
import br.com.fiap.umtdspo.domain.model.Autor;
import br.com.fiap.umtdspo.domain.repository.LivroRepository;
import br.com.fiap.umtdspo.domain.repository.AutorRepository;
import br.com.fiap.umtdspo.domain.service.LivroService;
import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class LivroServiceImpl implements LivroService {

    @Inject
    LivroRepository livroRepository;

    @Inject
    AutorRepository autorRepository;

    @Inject
    Logger logger;

    @Override
    public Livro criar(Livro livro) {
        try {
            livro.validarTitulo();
            livro.validarIsbn();

            Autor autor = autorRepository.buscarPorId(livro.getAutor().getId());
            livro.setAutor(autor);

            if (existeLivroComIsbn(livro.getIsbn())) {
                throw new RuntimeException("Já existe livro cadastrado com este ISBN: " + livro.getIsbn());
            }

            Livro livroSalvo = livroRepository.salvar(livro);
            logger.info("Livro criado com sucesso. ID: " + livroSalvo.getId());

            return livroSalvo;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autor não encontrado para criar livro: " + e.getMessage());
            throw new RuntimeException("Autor não encontrado: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao criar livro: " + e.getMessage());
            throw new RuntimeException("Falha ao criar livro: " + e.getMessage());
        }
    }

    @Override
    public Livro editar(Integer id, Livro livro) throws EntidadeNaoLocalizadaException {
        try {
            Livro livroExistente = livroRepository.buscarPorId(id);

            livro.validarTitulo();
            livro.validarIsbn();

            Autor autor = autorRepository.buscarPorId(livro.getAutor().getId());
            livro.setAutor(autor);

            if (!livroExistente.getIsbn().equals(livro.getIsbn()) &&
                    existeLivroComIsbn(livro.getIsbn())) {
                throw new RuntimeException("Já existe livro cadastrado com este ISBN: " + livro.getIsbn());
            }

            livro.setId(id);
            Livro livroAtualizado = livroRepository.editar(livro);
            logger.info("Livro atualizado com sucesso. ID: " + id);

            return livroAtualizado;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Livro não encontrado para edição. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao editar livro. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao editar livro: " + e.getMessage());
        }
    }

    @Override
    public Livro remover(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Livro livro = livroRepository.buscarPorId(id);
            livroRepository.remover(id);
            logger.info("Livro removido com sucesso. ID: " + id);
            return livro;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Livro não encontrado para remoção. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao remover livro. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao remover livro: " + e.getMessage());
        }
    }

    @Override
    public Livro localizar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Livro livro = livroRepository.buscarPorId(id);
            logger.info("Livro localizado. ID: " + id);
            return livro;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Livro não localizado. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar livro. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar livro: " + e.getMessage());
        }
    }

    @Override
    public List<Livro> listarTodos() {
        try {
            List<Livro> livros = livroRepository.buscarTodos();
            logger.info("Listados " + livros.size() + " livros");
            return livros;

        } catch (Exception e) {
            logger.error("Erro ao listar livros: " + e.getMessage());
            throw new RuntimeException("Falha ao listar livros: " + e.getMessage());
        }
    }

    @Override
    public List<Livro> listarPorAutor(Integer autorId) {
        try {
            autorRepository.buscarPorId(autorId);

            List<Livro> livros = livroRepository.buscarPorAutor(autorId);
            logger.info("Listados " + livros.size() + " livros do autor ID: " + autorId);
            return livros;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autor não encontrado. ID: " + autorId);
            throw new RuntimeException("Autor não encontrado: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao listar livros por autor. ID: " + autorId + ": " + e.getMessage());
            throw new RuntimeException("Falha ao listar livros por autor: " + e.getMessage());
        }
    }

    private boolean existeLivroComIsbn(String isbn) {
        try {
            livroRepository.buscarPorIsbn(isbn);
            return true;
        } catch (EntidadeNaoLocalizadaException e) {
            return false;
        }
    }
}