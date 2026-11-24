# Arquitetura

A aplicação usa uma arquitetura em camadas no Spring Boot:
- **Controller:** recebe requisições HTTP e expõe endpoints REST.
- **Service:** concentra regras de negócio.
- **Repository:** acessa o PostgreSQL com Spring Data JPA.
- **Model:** representa as entidades persistidas.
- **DTO:** define os dados recebidos e enviados pela API.
- **Security:** autenticação JWT.

A interface é servida pelo próprio Spring Boot a partir de `src/main/resources/static`:
- HTML para estrutura;
- CSS para apresentação;
- JavaScript para interagir com os endpoints `/api`.

Assim, em execução, interface e API usam o mesmo endereço e a mesma porta (`8080`). Não há servidor Vue/Vite separado.
