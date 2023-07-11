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
