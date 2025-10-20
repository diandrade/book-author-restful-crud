package br.com.fiap.umtdspo.interfaces.controller;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.interfaces.dto.AutorRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.AutorResponseDTO;

import java.util.List;

public interface AutorController {
    AutorResponseDTO criarAutor(AutorRequestDTO autorRequest) throws EntidadeNaoLocalizadaException;
    AutorResponseDTO editarAutor(Integer id, AutorRequestDTO autorRequest) throws EntidadeNaoLocalizadaException;
    AutorResponseDTO getAutorById(Integer id) throws EntidadeNaoLocalizadaException;
    void deleteAutor(Integer id) throws EntidadeNaoLocalizadaException;
    List<AutorResponseDTO> getAllAutores();
}
