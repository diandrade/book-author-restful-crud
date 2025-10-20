package br.com.fiap.umtdspo.interfaces.mapper;

import br.com.fiap.umtdspo.domain.model.Autor;
import br.com.fiap.umtdspo.interfaces.dto.AutorRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.AutorResponseDTO;

import java.util.List;

public interface AutorMapper {
    Autor toDomain(AutorRequestDTO dto);
    AutorResponseDTO toResponseDTO(Autor autor);
    List<AutorResponseDTO> toResponseDTOList(List<Autor> autores);
}
