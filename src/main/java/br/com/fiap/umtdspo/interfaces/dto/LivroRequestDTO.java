package br.com.fiap.umtdspo.interfaces.dto;

public class LivroRequestDTO {
    private String titulo;
    private String isbn;
    private Integer autorId; // Apenas o ID do autor

    // Construtores
    public LivroRequestDTO() {}

    public LivroRequestDTO(String titulo, String isbn, Integer autorId) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autorId = autorId;
    }

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getAutorId() { return autorId; }
    public void setAutorId(Integer autorId) { this.autorId = autorId; }
}