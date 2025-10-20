package br.com.fiap.umtdspo.interfaces.dto;

public class LivroResponseDTO {
    private Integer id;
    private String titulo;
    private String isbn;
    private AutorResponseDTO autor;

    // Construtores
    public LivroResponseDTO() {}

    public LivroResponseDTO(Integer id, String titulo, String isbn, AutorResponseDTO autor) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public AutorResponseDTO getAutor() { return autor; }
    public void setAutor(AutorResponseDTO autor) { this.autor = autor; }
}