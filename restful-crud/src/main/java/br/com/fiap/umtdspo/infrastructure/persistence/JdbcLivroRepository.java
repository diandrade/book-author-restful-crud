package br.com.fiap.umtdspo.infrastructure.persistence;

import br.com.fiap.umtdspo.domain.model.Livro;
import br.com.fiap.umtdspo.domain.model.Autor;
import br.com.fiap.umtdspo.domain.repository.LivroRepository;
import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcLivroRepository implements LivroRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcLivroRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private Livro mapResultSetToLivro(ResultSet rs) throws SQLException {
        Autor autor = new Autor(
                rs.getString("NM_AUTOR"),
                rs.getString("NM_EMAIL")
        );
        autor.setId(rs.getInt("ID_AUTOR"));

        Livro livro = new Livro(
                rs.getString("NM_TITULO"),
                rs.getString("CD_ISBN"),
                autor
        );
        livro.setId(rs.getInt("ID_LIVRO"));
        return livro;
    }

    @Override
    public Livro buscarPorId(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT l.ID_LIVRO, l.NM_TITULO, l.CD_ISBN, " +
                "a.ID_AUTOR, a.NM_AUTOR, a.NM_EMAIL " +
                "FROM T_CREST_LIVRO l " +
                "INNER JOIN T_CREST_AUTOR a ON l.ID_AUTOR = a.ID_AUTOR " +
                "WHERE l.ID_LIVRO = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLivro(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Livro não encontrado com ID: " + id);
                }
            }
        } catch (SQLException e) {
            throw new EntidadeNaoLocalizadaException("Erro ao buscar livro por ID: " + e.getMessage());
        }
    }

    @Override
    public Livro buscarPorIsbn(String isbn) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT l.ID_LIVRO, l.NM_TITULO, l.CD_ISBN, " +
                "a.ID_AUTOR, a.NM_AUTOR, a.NM_EMAIL " +
                "FROM T_CREST_LIVRO l " +
                "INNER JOIN T_CREST_AUTOR a ON l.ID_AUTOR = a.ID_AUTOR " +
                "WHERE l.CD_ISBN = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLivro(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Livro não encontrado com ISBN: " + isbn);
                }
            }
        } catch (SQLException e) {
            throw new EntidadeNaoLocalizadaException("Erro ao buscar livro por ISBN: " + e.getMessage());
        }
    }

    @Override
    public List<Livro> buscarTodos() {
        String sql = "SELECT l.ID_LIVRO, l.NM_TITULO, l.CD_ISBN, " +
                "a.ID_AUTOR, a.NM_AUTOR, a.NM_EMAIL " +
                "FROM T_CREST_LIVRO l " +
                "INNER JOIN T_CREST_AUTOR a ON l.ID_AUTOR = a.ID_AUTOR";

        List<Livro> livros = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                livros.add(mapResultSetToLivro(rs));
            }
            return livros;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar todos livros: " + e.getMessage());
        }
    }

    @Override
    public List<Livro> buscarPorAutor(Integer autorId) {
        String sql = "SELECT l.ID_LIVRO, l.NM_TITULO, l.CD_ISBN, " +
                "a.ID_AUTOR, a.NM_AUTOR, a.NM_EMAIL " +
                "FROM T_CREST_LIVRO l " +
                "INNER JOIN T_CREST_AUTOR a ON l.ID_AUTOR = a.ID_AUTOR " +
                "WHERE l.ID_AUTOR = ?";

        List<Livro> livros = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, autorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livros.add(mapResultSetToLivro(rs));
                }
                return livros;
            }
        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar livros por autor: " + e.getMessage());
        }
    }

    @Override
    public Livro salvar(Livro livro) {
        String sql = "INSERT INTO T_CREST_LIVRO (NM_TITULO, CD_ISBN, ID_AUTOR) VALUES (?, ?, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_LIVRO"})) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getIsbn());
            stmt.setInt(3, livro.getAutor().getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    livro.setId(generatedKeys.getInt(1));
                }
            }
            return livro;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao salvar livro: " + e.getMessage());
        }
    }

    @Override
    public Livro editar(Livro livro) throws EntidadeNaoLocalizadaException {
        String sql = "UPDATE T_CREST_LIVRO SET NM_TITULO = ?, CD_ISBN = ?, ID_AUTOR = ? WHERE ID_LIVRO = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getIsbn());
            stmt.setInt(3, livro.getAutor().getId());
            stmt.setInt(4, livro.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Livro não encontrado com ID: " + livro.getId());
            }
            return livro;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao editar livro: " + e.getMessage());
        }
    }

    @Override
    public void remover(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "DELETE FROM T_CREST_LIVRO WHERE ID_LIVRO = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Livro não encontrado com ID: " + id);
            }
        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover livro: " + e.getMessage());
        }
    }
}