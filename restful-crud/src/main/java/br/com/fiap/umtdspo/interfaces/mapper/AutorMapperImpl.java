package br.com.fiap.umtdspo.interfaces.mapper;

import br.com.fiap.umtdspo.domain.model.Autor;
import br.com.fiap.umtdspo.interfaces.dto.AutorRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.AutorResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AutorMapperImpl implements AutorMapper {

    @Override
    public Autor toDomain(AutorRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Autor autor = new Autor(dto.getNome(), dto.getEmail());

        if (dto.getId() != null) {
            autor.setId(dto.getId());
        } else {
            autor.setId(0);
        }

        return autor;
    }

    @Override
    public AutorResponseDTO toResponseDTO(Autor autor) {
        if (autor == null) {
            return null;
        }
        AutorResponseDTO response = new AutorResponseDTO();
        response.setId(autor.getId());
        response.setNome(autor.getNome());
        response.setEmail(autor.getEmail());
        return response;
    }

    @Override
    public List<AutorResponseDTO> toResponseDTOList(List<Autor> autores) {
        if (autores == null) {
            return List.of();
        }
        return autores.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}