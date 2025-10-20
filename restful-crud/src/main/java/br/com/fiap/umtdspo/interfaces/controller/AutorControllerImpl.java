package br.com.fiap.umtdspo.interfaces.controller;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.domain.model.Autor;
import br.com.fiap.umtdspo.domain.service.AutorService;
import br.com.fiap.umtdspo.interfaces.dto.AutorRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.AutorResponseDTO;
import br.com.fiap.umtdspo.interfaces.mapper.AutorMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class AutorControllerImpl implements AutorController{
    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @Inject
    public AutorControllerImpl(AutorService autorService, AutorMapper autorMapper) {
        this.autorService = autorService;
        this.autorMapper = autorMapper;
    }

    @Override
    public AutorResponseDTO criarAutor(AutorRequestDTO autorRequest) throws EntidadeNaoLocalizadaException {
        System.out.println("DEBUG 1 - criarAutor iniciado. Request ID: " + autorRequest.getId());

        try {
            System.out.println("DEBUG 2 - Chamando autorMapper.toDomain");
            Autor autor = autorMapper.toDomain(autorRequest);
            System.out.println("DEBUG 3 - Autor domain criado. ID: " + autor.getId() + ", Nome: " + autor.getNome());

            System.out.println("DEBUG 4 - Chamando autorService.criar");
            Autor autorCriado = this.autorService.criar(autor);
            System.out.println("DEBUG 5 - Autor criado no service. ID: " + autorCriado.getId());

            System.out.println("DEBUG 6 - Chamando autorMapper.toResponseDTO");
            return autorMapper.toResponseDTO(autorCriado);

        } catch (Exception e) {
            System.out.println("DEBUG ERROR - Erro em criarAutor: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public AutorResponseDTO editarAutor(Integer id, AutorRequestDTO autorRequest) throws EntidadeNaoLocalizadaException {
        Autor autorExistente = this.autorService.localizar(id);

        autorExistente.setNome(autorRequest.getNome());
        autorExistente.setEmail(autorRequest.getEmail());
        Autor autorAtualizado = this.autorService.editar(id, autorExistente);
        return autorMapper.toResponseDTO(autorAtualizado);
    }

    @Override
    public AutorResponseDTO getAutorById(Integer id) throws EntidadeNaoLocalizadaException {
        Autor autor = this.autorService.localizar(id);
        return autorMapper.toResponseDTO(autor);
    }

    @Override
    public void deleteAutor(Integer id) throws EntidadeNaoLocalizadaException {
        this.autorService.remover(id);
    }

    @Override
    public List<AutorResponseDTO> getAllAutores() {
        List<Autor> autores = autorService.listarTodos();
        return autorMapper.toResponseDTOList(autores);
    }
}
