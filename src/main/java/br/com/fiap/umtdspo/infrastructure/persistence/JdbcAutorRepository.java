package br.com.fiap.umtdspo.infrastructure.persistence;

import br.com.fiap.umtdspo.domain.exception.EntidadeNaoLocalizadaException;
import br.com.fiap.umtdspo.domain.model.Autor;
import br.com.fiap.umtdspo.domain.repository.AutorRepository;
import br.com.fiap.umtdspo.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcAutorRepository implements AutorRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcAutorRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private Autor mapResultSetToAutor(ResultSet rs) throws SQLException {
        Autor autor = new Autor(rs.getString("NM_AUTOR"), rs.getString("NM_EMAIL"));
        autor.setId(rs.getInt("ID_AUTOR"));
        return autor;
    }

    @Override
    public Autor buscarPorId(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT ID_AUTOR, NM_AUTOR, NM_EMAIL FROM T_CREST_AUTOR WHERE ID_AUTOR = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAutor(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Autor não encontrado com ID: " + id);
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar autor por ID: " + e.getMessage());
        }
    }

    @Override
    public List<Autor> buscarTodos() {
        String sql = "SELECT ID_AUTOR, NM_AUTOR, NM_EMAIL FROM T_CREST_AUTOR";
        List<Autor> autores = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                autores.add(mapResultSetToAutor(rs));
            }

            return autores;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar todos autores: " + e.getMessage());
        }
    }

    @Override
    public Autor salvar(Autor autor) {
        String sql = "INSERT INTO T_CREST_AUTOR (NM_AUTOR, NM_EMAIL) VALUES (?, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_AUTOR"})) {
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    autor.setId(generatedKeys.getInt(1));
                }
            }
            return autor;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao salvar autor: " + e.getMessage());
        }
    }

    @Override
    public Autor editar(Autor autor) throws EntidadeNaoLocalizadaException {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("ID do autor não pode ser nulo para edição");
        }

        String sql = "UPDATE T_CREST_AUTOR SET NM_AUTOR = ?, NM_EMAIL = ? WHERE ID_AUTOR = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getEmail());
            stmt.setInt(3, autor.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Autor não encontrado com ID: " + autor.getId());
            }

            return autor;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao editar autor: " + e.getMessage());
        }
    }

    @Override
    public void remover(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "DELETE FROM T_CREST_AUTOR WHERE ID_AUTOR = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Autor não encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover autor: " + e.getMessage());
        }
    }

    @Override
    public Autor buscarPorEmail(String email) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT ID_AUTOR, NM_AUTOR, NM_EMAIL FROM T_CREST_AUTOR WHERE NM_EMAIL = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAutor(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Autor não encontrado com email: " + email);
                }
            }
        } catch (SQLException e) {
            throw new EntidadeNaoLocalizadaException("Erro ao buscar autor por email: " + e.getMessage());
        }
    }
}