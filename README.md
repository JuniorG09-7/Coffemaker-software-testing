# CoffeeMaker - Unit Testing

Projeto final do curso Introduction to Software Testing (Coursera), aplicando testes unitários no sistema CoffeeMaker — uma máquina de café virtual desenvolvida em Java.

O CoffeeMaker é um sistema que permite cadastrar receitas, gerenciar ingredientes e simular a compra de cafés. O código-fonte da aplicação foi fornecido pelo curso, e minha responsabilidade foi implementar os testes.

## O que foi testado

- Adição de receitas válidas e inválidas
- Compra de café com saldo suficiente e insuficiente
- Manipulação de ingredientes no estoque
- Tratamento de exceções esperadas
- Isolamento da classe CoffeeMaker do RecipeBook usando mocks

## Técnicas aplicadas

**JUnit 4** — testes unitários que verificam o comportamento da aplicação em diferentes cenários, garantindo que cada funcionalidade retorna o resultado esperado.

**JaCoCo** — medição de cobertura de branches: garante que cada `if` do código seja testado nos dois caminhos possíveis (verdadeiro e falso), não apenas que as linhas foram executadas.

**Mockito** — uso de objetos simulados (mocks) para isolar a classe CoffeeMaker da implementação real do RecipeBook, permitindo testar o comportamento da máquina de forma controlada.

## Estrutura do projeto

src/
├── main/java/          # Código-fonte da aplicação (fornecido pelo curso)
└── test/java/          # Testes implementados por mim
    └── CoffeeMakerTest.java
build/
└── reports/
    ├── tests/test/index.html        # Relatório JUnit
    └── jacoco/test/html/index.html  # Relatório de cobertura

## Como rodar

**Pré-requisito:** Java 8 JDK. O projeto não é compatível com versões superiores. Certifique-se de que a variável `JAVA_HOME` aponta para o Java 8.

Clone o repositório:

git clone https://github.com/JuniorG09-7/Coffemaker-software-testing.git
cd Coffemaker-software-testing

Execute o build:

Windows: gradlew.bat build

Linux/macOS: ./gradlew build

O comando vai compilar o código, executar todos os testes e gerar os relatórios automaticamente.

## Visualizando os relatórios

Após o build, abra os arquivos no navegador:

**Relatório JUnit** — mostra quais testes passaram e quais falharam:
build/reports/tests/test/index.html

**Relatório JaCoCo** — mostra a porcentagem de cobertura por classe e branch:
build/reports/jacoco/test/html/index.html

No Windows, arraste o arquivo `.html` direto para o navegador ou cole o caminho precedido de `file:///`.

## Tecnologias

| Ferramenta | Versão | Finalidade |
|---|---|---|
| Java | 8 | Linguagem principal |
| JUnit | 4 | Testes unitários |
| JaCoCo | — | Cobertura de código |
| Mockito | — | Mocks e isolamento |
| Gradle | — | Build e automação |
