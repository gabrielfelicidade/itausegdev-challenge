# Desafio Itaú Seguros

## Como executar o projeto

Para executar o projeto é necessário somente rodar o seguinte comando (utilizado como base Windows)
```
./gradlew bootRun
```

## Racional das decisões

- Banco de dados H2: utilizei o H2 como base de dados por conta da agilidade para iniciar o desenvolvimento e não ser necessário subir algum banco como serviço, como desvantagem em utilizar o H2 em memória, ele não é persistente, ou seja, tudo que é executado é perdido após derrubar o serviço
- Estrutura dos arquivos: utilizei como base uma junção de MVC e Clean Architecture juntamente com princípios de boas práticas de programação. Utilizar MVC e Clean Architecture como base foi simplesmente por conforto e hábito.
- Bibliotecas: optei por utilizar algumas bibliotecas para diminuir o boilerplate, como Lombok, para permitir a criação de objetos de forma mais simples e EasyRandom para gerar dados aleatórios durante os testes.
- Testes: por conta da baixa complexidade de negócio, optei por cobrir os casos de testes com testes unitários para garantir o funcionamento base do código e testes de integração para avaliar principalmente o formato de entrada/saída e as validações no bean de entrada.

## Suposições

- Supus que o padrão de cálculo era de considerar múltiplas casas, porém, tanto para salvar quanto retornar na tela optei por utilizar 2 casas decimais.


## Adicional

- Apesar de não ter incluído nenhuma ferramenta de métrica, olhando para essa lógica de negócio especificamente talvez uma melhoria seria metrificar a quantidade de falhas de criação de produto por período para analisarmos a principal causa desses problemas.