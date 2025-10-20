# 📚 Sistema de Gerenciamento de Biblioteca - REST API

Uma API RESTful desenvolvida com **Quarkus** para gerenciamento de autores e livros, seguindo os princípios da **Clean Architecture**.

---

## 🚀 Tecnologias

- **Java 21** – Linguagem de programação  
- **Quarkus 3.28.4** – Framework Supersonic Subatomic Java  
- **Oracle Database** – Banco de dados relacional  
- **JAX-RS** – API REST  
- **JDBC** – Acesso a dados  
- **Maven** – Gerenciamento de dependências  

---

## 🏗️ Arquitetura do Projeto

```
📦 umtdspo
├── 📁 domain                # Camada de Domínio
│   ├── model                # Entidades (Autor, Livro)
│   ├── repository           # Interfaces de repositório
│   ├── service              # Interfaces de serviço
│   └── exception            # Exceções de domínio
├── 📁 application           # Camada de Aplicação
│   └── service              # Implementações de serviço
├── 📁 infrastructure        # Camada de Infraestrutura
│   ├── api/rest             # Controllers REST
│   ├── persistence          # Implementações JDBC
│   ├── config               # Configurações
│   └── exception            # Exceções de infraestrutura
└── 📁 interfaces            # Camada de Interface
    ├── controller           # Controllers
    ├── dto                  # Data Transfer Objects
    └── mapper               # Mappers (DTO ↔ Domain)
```

---

## 📋 Endpoints da API

### 👥 Autores

| Método   | Endpoint              | Descrição                |
|----------|-----------------------|--------------------------|
| `POST`   | `/authors`            | Criar novo autor         |
| `GET`    | `/authors`            | Listar todos autores     |
| `GET`    | `/authors/{id}`       | Buscar autor por ID      |
| `GET`    | `/authors/{id}/books` | Listar livros do autor   |
| `PUT`    | `/authors/{id}`       | Atualizar autor          |
| `DELETE` | `/authors/{id}`       | Deletar autor            |

### 📖 Livros

| Método   | Endpoint                    | Descrição                 |
|----------|-----------------------------|---------------------------|
| `POST`   | `/books`                    | Criar novo livro          |
| `GET`    | `/books`                    | Listar todos livros       |
| `GET`    | `/books/{id}`               | Buscar livro por ID       |
| `GET`    | `/books/author/{authorId}`  | Listar livros por autor   |
| `PUT`    | `/books/{id}`               | Atualizar livro           |
| `DELETE` | `/books/{id}`               | Deletar livro             |

---

## 🛠️ Como Executar

### ✅ Pré-requisitos

- **Java 21**  
- **Maven 3.8+**  
- **Oracle Database**

---

### 📦 Passo a Passo

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/umtdspo.git
   cd umtdspo
   ```

2. Configure o banco de dados no arquivo `src/main/resources/application.properties`

3. Execute o projeto com:
   ```bash
   mvn quarkus:dev
   ```

---

## 📝 Exemplos de Uso

### Criar Autor
```bash
POST http://localhost:8080/authors
Content-Type: application/json

{
  "nome": "Machado de Assis",
  "email": "machado@email.com"
}
```

### Criar Livro
```bash
POST http://localhost:8080/books
Content-Type: application/json

{
  "titulo": "Dom Casmurro",
  "isbn": "9788535914843",
  "autorId": 1
}
```

### Listar Livros por Autor
```bash
GET http://localhost:8080/authors/1/books
```

---

## 🧪 Testando a API

### Com Insomnia/Postman
1. Importe a coleção de endpoints.  
2. Teste todas as operações CRUD.

### Com `curl`
```bash
# Listar autores
curl http://localhost:8080/authors

# Criar autor
curl -X POST http://localhost:8080/authors   -H "Content-Type: application/json"   -d '{"nome": "Teste", "email": "teste@email.com"}'
```

---

## 🗂️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/
│   │       └── com/
│   │           └── fiap/
│   │               └── umtdspo/
│   │                   ├── domain/
│   │                   │   ├── model/
│   │                   │   ├── repository/
│   │                   │   ├── service/
│   │                   │   └── exception/
│   │                   ├── application/
│   │                   │   └── service/
│   │                   ├── infrastructure/
│   │                   │   ├── api/rest/
│   │                   │   ├── persistence/
│   │                   │   ├── config/
│   │                   │   └── exception/
│   │                   └── interfaces/
│   │                       ├── controller/
│   │                       ├── dto/
│   │                       └── mapper/
│   └── resources/
│       └── application.properties
└── test/
```

---

## 🔧 Desenvolvimento

### Comandos Úteis

```bash
# Desenvolvimento com hot reload
mvn quarkus:dev

# Compilar
mvn clean compile

# Executar testes
mvn test

# Empacotar
mvn package

# Verificar dependências
mvn dependency:tree
```

💡 O Quarkus oferece **hot reload** durante o desenvolvimento. Basta salvar o arquivo e as mudanças são aplicadas automaticamente.

---

## 📊 Modelo de Dados

### Autor
- `ID_AUTOR (PK)`: Identificador único  
- `NM_AUTOR`: Nome do autor  
- `NM_EMAIL`: Email único do autor  

### Livro
- `ID_LIVRO (PK)`: Identificador único  
- `NM_TITULO`: Título do livro  
- `CD_ISBN`: ISBN único do livro  
- `ID_AUTOR (FK)`: Referência ao autor  

---

## 🤝 Contribuindo

1. Faça um fork do projeto  
2. Crie uma branch para sua feature:
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. Commit suas mudanças:
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. Push para a branch:
   ```bash
   git push origin feature/AmazingFeature
   ```
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo `LICENSE` para mais detalhes.

---

## 👥 Autor

**Seu Nome** – [@seu-usuario](https://github.com/seu-usuario)

---

## 🙏 Agradecimentos

- Equipe FIAP  
- Comunidade Quarkus  
- Oracle Database
