package br.com.fiap.umtdspo.domain.model;

import br.com.fiap.umtdspo.domain.exception.ValidacaoDeDominioException;

public class Livro {
    private Integer id;
    private String titulo;
    private String isbn;
    private Autor autor;

    public Livro(String titulo, String isbn, Autor autor) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
    }

    // Validações
    public void validarTitulo() {
        if (titulo == null || titulo.isBlank()) {
            throw new ValidacaoDeDominioException("Título não pode estar vazio");
        }
        if (titulo.length() > 255) {
            throw new ValidacaoDeDominioException("Título deve ter no máximo 255 caracteres");
        }
    }

    public void validarIsbn() {
        if (isbn == null || isbn.isBlank()) {
            throw new ValidacaoDeDominioException("ISBN não pode estar vazio");
        }
        if (isbn.length() != 10 && isbn.length() != 13) {
            throw new ValidacaoDeDominioException("ISBN deve ter 10 ou 13 caracteres");
        }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
        validarTitulo();
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
        validarIsbn();
    }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
}