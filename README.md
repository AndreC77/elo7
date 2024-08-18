# Desafio Elo7 Mover sondas API

| Data       | Autor        | Comentários | Versão |
|------------|--------------| --- | --- |
| 16/08/2024 | Andre Coelho | Versão de lançamento | 1.0.0-RELEASE |

---

## Objetivo

O objetivo do sistema é ser a implementação do desafio de controlar sondas em outros planetas por meio de comandos.

---

## Inicializando o Sistema

### Start e utilização da API

Construa a aplicação com __mvn clean install__;

---

Execute o comando __docker-compose__ no terminal;

Após as imagens serem criadas e API for executada com sucesso podemos acessar:

### Banco de Dodos MySQL pelo PHPmyadmin
* http://localhost:8000/index.php?route=/&route=%2F&db=desafio_db

### API via Swagger

* http://localhost:4500/swagger-ui/index.html#/

### Tecnologias

* Java 21
* Maven
* Spring boot 3.2.2

### Sobre a API

**Planet(Planeta) e PlanetController:**
- Contém os campos, **"id"**, **"name"**, **"maxX"**, **"maxY"** e **"probes"**.
- Pode ser enviado uma requisição POST para o endereço: "/api/v1/planet", com um Body JSON contendo os campos **"name"**, **"maxX"** e **"maxY"**.
- É possível analisar todos os Planetas criados, enviando uma requisição GET para o endereço: "/api/v1/planet".
- É possível analisar um Planeta especifico por id enviando uma requisição GET para o endereço: "/api/v1/planet/{planetId}".

**SpaceProbe(Sonda espacial) e SpaceProbeController:**
- Contém os campos, **"id"**, **"name"**, **"positionX"**, **"positionY"**, **"direction"** e **"planet"**.
- Pode ser enviado uma requisição POST para o endereço: "/api/v1/probe", com um Body JSON contendo os campos **"name"**, **"positionX"**, **"positionY"**, **"direction"** e **"planetId"**.
- É possível analisar todos as sondas criados, enviando uma requisição GET para o endereço: "/api/v1/probe".
- É possível analisar uma sonda especifica por id enviando uma requisição GET para o endereço: "/api/v1/probe/{probeId}".
- É possível desativar uma sonda enviando uma requisição DELETE para o endereço: "/api/v1/probe/disableProbe" enviando o id da sonda.
- Mover a sonda é via uma requisição POST para o endereço: "/api/v1/probe/move" com um Body JSON contendo os campos **"commands"** e **"spaceProbeId"**.
- ndo os campos

**Regras de Negócio:**

- Não é possível haver mais sondas do que posições do planeta. É possível Haver apenas uma sonda por posição no planeta.
- As sondas podem ser pousadas em qualquer area vazia do planeta, caso seja uma area ocupada API ira subir um erro.
- Caso a sonda seja movida para uma area ocupada a API ira subir um erro.


