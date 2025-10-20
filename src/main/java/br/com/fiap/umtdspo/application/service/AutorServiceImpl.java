package br.com.fiap.umtdspo.application.service;

import br.com.fiap.umtdspo.domain.model.Autor;
import br.com.fiap.umtdspo.domain.repository.AutorRepository;
import br.com.fiap.umtdspo.domain.service.AutorService;
import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class AutorServiceImpl implements AutorService {

    @Inject
    AutorRepository autorRepository;

    @Inject
    Logger logger;

    @Override
    public Autor criar(Autor autor) {
        System.out.println("DEBUG SERVICE 1 - criar iniciado. Autor ID: " + autor.getId());

        try {
            System.out.println("DEBUG SERVICE 2 - Validando nome e email");
            autor.validarNome();
            autor.validarEmail();

            System.out.println("DEBUG SERVICE 3 - Verificando email existente: " + autor.getEmail());
            if (existeAutorComEmail(autor.getEmail())) {
                throw new RuntimeException("Já existe autor cadastrado com este email: " + autor.getEmail());
            }

            System.out.println("DEBUG SERVICE 4 - Chamando autorRepository.salvar");
            Autor autorSalvo = autorRepository.salvar(autor);
            System.out.println("DEBUG SERVICE 5 - Autor salvo no repository. ID: " + autorSalvo.getId());

            return autorSalvo;

        } catch (Exception e) {
            System.out.println("DEBUG SERVICE ERROR - Erro ao criar autor: " + e.getMessage());
            throw new RuntimeException("Falha ao criar autor: " + e.getMessage());
        }
    }

    @Override
    public Autor editar(Integer id, Autor autor) throws EntidadeNaoLocalizadaException {
        try {
            Autor autorExistente = autorRepository.buscarPorId(id);

            autor.validarNome();
            autor.validarEmail();

            if (!autorExistente.getEmail().equals(autor.getEmail()) &&
                    existeAutorComEmail(autor.getEmail())) {
                throw new RuntimeException("Já existe autor cadastrado com este email: " + autor.getEmail());
            }

            autor.setId(id);
            Autor autorAtualizado = autorRepository.editar(autor);
            logger.info("Autor atualizado com sucesso. ID: " + id);

            return autorAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autor não encontrado para edição. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao editar autor. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao editar autor: " + e.getMessage());
        }
    }

    @Override
    public Autor remover(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Autor autor = autorRepository.buscarPorId(id);

            autorRepository.remover(id);
            logger.info("Autor removido com sucesso. ID: " + id);

            return autor;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autor não encontrado para remoção. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao remover autor. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao remover autor: " + e.getMessage());
        }
    }

    @Override
    public Autor localizar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Autor autor = autorRepository.buscarPorId(id);
            logger.info("Autor localizado. ID: " + id);
            return autor;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autor não localizado. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar autor. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar autor: " + e.getMessage());
        }
    }

    @Override
    public List<Autor> listarTodos() {
        try {
            List<Autor> autores = autorRepository.buscarTodos();
            logger.info("Listados " + autores.size() + " autores");
            return autores;

        } catch (Exception e) {
            logger.error("Erro ao listar autores: " + e.getMessage());
            throw new RuntimeException("Falha ao listar autores: " + e.getMessage());
        }
    }

    private boolean existeAutorComEmail(String email) {
        try {
            autorRepository.buscarPorEmail(email);
            return true;
        } catch (EntidadeNaoLocalizadaException e) {
            return false;
        }
    }
}