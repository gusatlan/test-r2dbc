# Testes com R2DBC
2023-07-11

### Prós

* Reativo
    * Suporta MariaDB, MySQL, PostgreSQL;
* Compatível com reactive (WebFlux) do Spring Data/Boot;

### Contras

* Falta de suporte JPA (Anotações);
* Não faz relacionamentos, o desenvolvedor tem que relacionar as tabelas de forma manual;
* Até aonde pesquisei, o campo id deve ser autoincrement para funcionar com o R2DBC, passando um id diferente de nulo é assumido que é um update e não um insert;


### Resumo

Não acho adequado usá-lo, perde-se o ORM existente do JPA.
Outros bancos NoSQL já tem drivers reativos maduros.
É uma quebra no modelo de desenvolvimento com JPA, tornando mais complicado um desenvolvedor júnior implementar uma solução baseado no mesmo.


### Implementação

* Criado AuthorRepository, BookRepository e BookAuthorRepository;
* Criado os services AuthorService, BookService, BookAuthorService para tratamento de busca e persistência dos dados;
* Criado BookAuthorFacade, o mesmo cuida da persistência dos dados e a associação entre Book e Author;
* Criado classes de persistência Author, Book e BookAuthor;
* Criado classes DTO AuthorDTO, BookDTO e BookAuthorDTO para o response dos endpoints;
* Criado endpoints REST para recuperação e persistência dos dados;
* Criado endpoint para teste de stress;

#### Prós da implementação reativa

O fluxo de ponta à ponta é reativo, não existe bloqueio, aumentando assim a performance e escalabilidade
Tem um footprint de memória menor.

#### Contras da implementação reativa

É um paradigma de programação diferente, a curva de aprendizado é maior.
Para uso com banco de dados relacionais, não existe ainda um driver reativo maduro

