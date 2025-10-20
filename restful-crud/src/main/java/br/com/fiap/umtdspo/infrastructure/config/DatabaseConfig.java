package br.com.fiap.umtdspo.infrastructure.config;

import br.com.fiap.umtdspo.domain.repository.AutorRepository;
import br.com.fiap.umtdspo.domain.repository.LivroRepository;
import br.com.fiap.umtdspo.infrastructure.persistence.DatabaseConnection;
import br.com.fiap.umtdspo.infrastructure.persistence.DatabaseConnectionImpl;
import br.com.fiap.umtdspo.infrastructure.persistence.JdbcAutorRepository;
import br.com.fiap.umtdspo.infrastructure.persistence.JdbcLivroRepository;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class DatabaseConfig {

    @Produces
    @ApplicationScoped
    public DatabaseConnection databaseConnection(AgroalDataSource dataSource) {
        return new DatabaseConnectionImpl(dataSource);
    }

    @Produces
    @ApplicationScoped
    public AutorRepository autorRepository(DatabaseConnection databaseConnection) {
        return new JdbcAutorRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public LivroRepository livroRepository(DatabaseConnection databaseConnection) {
        return new JdbcLivroRepository(databaseConnection);
    }
}
