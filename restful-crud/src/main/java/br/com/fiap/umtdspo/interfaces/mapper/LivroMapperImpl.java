package br.com.fiap.umtdspo.interfaces.mapper;

import br.com.fiap.umtdspo.domain.model.Livro;
import br.com.fiap.umtdspo.domain.model.Autor;
import br.com.fiap.umtdspo.interfaces.dto.LivroRequestDTO;
import br.com.fiap.umtdspo.interfaces.dto.LivroResponseDTO;
import br.com.fiap.umtdspo.interfaces.dto.AutorResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LivroMapperImpl implements LivroMapper {

    @Inject
    AutorMapper autorMapper;

    @Override
    public Livro toDomain(LivroRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Autor autor = new Autor("", "");
        autor.setId(dto.getAutorId());

        return new Livro(dto.getTitulo(), dto.getIsbn(), autor);
    }

    @Override
    public LivroResponseDTO toResponseDTO(Livro livro) {
        if (livro == null) {
            return null;
        }
        AutorResponseDTO autorDTO = autorMapper.toResponseDTO(livro.getAutor());

        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                autorDTO
        );
    }

    @Override
    public List<LivroResponseDTO> toResponseDTOList(List<Livro> livros) {
        if (livros == null) {
            return List.of();
        }
        return livros.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}