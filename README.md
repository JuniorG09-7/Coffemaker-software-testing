# ☕ CoffeeMaker — Testes de Software

Projeto final do curso **Introduction to Software Testing** (Coursera), aplicado ao sistema pedagógico **CoffeeMaker** — uma máquina de café virtual desenvolvida em Java.

O objetivo foi colocar em prática três pilares fundamentais de testes de software: testes unitários com JUnit, medição de cobertura com JaCoCo e isolamento de dependências com Mockito.

---

## 📚 O que foi implementado

### 🧪 JUnit — Testes Unitários
Testes que verificam o comportamento da aplicação em diferentes cenários: adicionar receitas válidas e inválidas, comprar café com saldo suficiente ou insuficiente, manipular ingredientes e tratar exceções esperadas.

### 📊 JaCoCo — Cobertura de Branches
Análise de cobertura de ramificações (branch coverage): garantir que cada `if` do código seja testado nos dois caminhos possíveis (verdadeiro e falso), não apenas em linhas executadas.

### 🎭 Mockito — Mocks e Isolamento
Uso de objetos simulados (mocks) para isolar a classe `CoffeeMaker` da implementação real do `RecipeBook`. Isso permite testar o comportamento da máquina de café de forma controlada, verificando as interações entre os objetos.

---

## 🗂️ Estrutura do Projeto

```
CoffeeMaker_Mock_Assign/
├── src/
│   ├── main/java/          # Código-fonte da aplicação (não modificar)
│   │   └── edu/ncsu/csc326/coffeemaker/
│   │       ├── CoffeeMaker.java
│   │       ├── Recipe.java
│   │       ├── RecipeBook.java (interface)
│   │       ├── Inventory.java
│   │       └── exceptions/
│   └── test/java/          # Código de testes (implementado por mim)
│       └── edu/ncsu/csc326/coffeemaker/
│           └── CoffeeMakerTest.java
├── build/
│   └── reports/
│       ├── tests/test/index.html     # Relatório JUnit
│       └── jacoco/test/html/index.html  # Relatório de cobertura
├── build.gradle            # Configuração do build (Gradle)
├── gradlew                 # Script de build para Linux/macOS
├── gradlew.bat             # Script de build para Windows
├── Requirements-CoffeeMaker.pdf
├── ClassDiagram-CoffeeMaker.pdf
└── SequenceDiagram-CoffeeMaker.pdf
```

---

## ⚙️ Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- **Java 8 JDK** — [Download aqui](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

> ⚠️ O projeto **não é compatível com Java 9 ou superior**. Certifique-se de que a variável de ambiente `JAVA_HOME` aponta para o Java 8.

---

## 🚀 Como rodar o projeto

### Pelo terminal (recomendado)

**1. Clone o repositório**
```bash
git clone https://github.com/JuniorG09-7/Coffemaker-software-testing.git
cd Coffemaker-software-testing
```

**2. Execute o build completo**

No Windows:
```bash
gradlew.bat build
```

No Linux/macOS:
```bash
./gradlew build
```

Esse comando vai:
- Compilar o código-fonte
- Executar todos os testes
- Gerar os relatórios de cobertura

---

## 📈 Visualizando os Relatórios

Após o build, os relatórios são gerados automaticamente na pasta `build/reports/`.

### Relatório JUnit (resultado dos testes)
Abra no navegador:
```
build/reports/tests/test/index.html
```

Você verá quais testes passaram ✅, quais falharam ❌ e o tempo de execução de cada um.

### Relatório JaCoCo (cobertura de código)
Abra no navegador:
```
build/reports/jacoco/test/html/index.html
```

Você verá a porcentagem de cobertura por classe, método e branch (ramificação). Classes em verde têm boa cobertura; em vermelho, precisam de mais testes.

> 💡 **Dica:** No Windows, você pode arrastar o arquivo `.html` direto para o navegador, ou colar o caminho completo na barra de endereço precedido de `file:///`.

---

## 🔍 Tecnologias utilizadas

| Ferramenta | Versão | Finalidade |
|------------|--------|------------|
| Java       | 8      | Linguagem principal |
| JUnit      | 4      | Testes unitários |
| JaCoCo     | —      | Cobertura de código |
| Mockito    | —      | Mocks e stubs |
| Gradle     | —      | Build e automação |

---

## 👨‍💻 Autor

**José Gicivaldo**
Curso: Introduction to Software Testing — Coursera
Repositório: [github.com/JuniorG09-7/Coffemaker-software-testing](https://github.com/JuniorG09-7/Coffemaker-software-testing)
