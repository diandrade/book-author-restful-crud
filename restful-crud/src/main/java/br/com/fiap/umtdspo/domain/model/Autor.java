package br.com.fiap.umtdspo.domain.model;

import br.com.fiap.umtdspo.domain.exception.ValidacaoDeDominioException;

public class Autor {
    private Integer id;
    private String nome;
    private String email;

    public Autor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public void validarNome(){
        if (nome == null || nome.isEmpty() || nome.isBlank()) {
            throw new ValidacaoDeDominioException("Nome está vazio.");
        }

        String[] palavras = nome.trim().split("\\s+");
        if (palavras.length < 2 || palavras[0].length() < 3 || palavras[1].length() < 3){
            throw new ValidacaoDeDominioException("Nome deve ter pelo menos duas palavras e cada palavra pelo menos três caracteres.");
        }

        if (nome.length() > 100){
            throw new ValidacaoDeDominioException("Nome deve ter menos de 100 caracteres.");
        }
    }

    public void validarEmail(){
        final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(regex)) {
            throw new ValidacaoDeDominioException("Formato de email inválido.");
        }

        if (email.length() > 255){
            throw new ValidacaoDeDominioException("Email deve ter menos de 255 caracteres.");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        validarNome();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        validarEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
