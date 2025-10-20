package br.com.fiap.umtdspo.interfaces.mapper;

import br.com.fiap.umtdspo.domain.model.Livro;
import br.com.fiap.umtdspo.interfaces.dto.LivroRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.LivroResponseDTO;
import java.util.List;

public interface LivroMapper {
    Livro toDomain(LivroRequestDTO dto);
    LivroResponseDTO toResponseDTO(Livro livro);
    List<LivroResponseDTO> toResponseDTOList(List<Livro> livros);
}