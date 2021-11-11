# CorretoraDesafio
Olá, eu implementei este projeto utilizando visual studio code, para os testes unitários utilizei o Maven e JUnity.

Gostaria de começar a explicar que construí o código pensando muito no que venho estudado a algum tempo sobre Clean Code e SOLID de acordo com a visão e livros do Uncle Bob (Robert C. Martin). Pensando no DIP ( Inversão de Dependência) eu criei as interfaces para tanto proteger métodos que não devem ser acessados, e facilitar o teste no JUnity, uma vez que precisa ser “public” para ter acesso ao teste diretamente e deixar o teste e código o mais simples e limpo possível. Outra vantagem disso seria poder alterar detalhes de implementação, refatorar, otimizar etc. e quem está acessando pela interface não será afetado, deixando o código desacoplado. 

Procurei utilizar da melhor forma possível o SRP, deixando cada classe e cada método com apenas uma responsabilidade. Também pensei no uso de interface para seguir da melhor forma possível o ISP para evitar dependências. Logo, nos testes o tanto quanto foi possível, procurei deixar os métodos menos dependentes possível. Pensei no OCP pensando em componentes que podem ser facilmente inseridos em conta corrente, ou ainda os componentes presentes no exemplo em conta corrente podem mudar de comportamento sem afetar o mesmo desde que respeitem a interface. O código foi feito seguindo princípios de TDD. Desenhei o diagrama da figura abaixo para ilustrar como pensei na arquitetura para o dado problema. 

Procurei Separar as “Regras de Negócio” da implementação dos detalhes técnicos. Dessa forma existe uma interface com as funções de comunicação com o banco de dados, e separado a implementação técnica que faria a comunicação com o banco de dados. Dessa forma todo código fica protegido de mudança em regras técnicas, mudança de banco de dados caso haja a necessidade de mudar de banco de dados, framework etc. 

Procurei na medida do possível tratar todas as possíveis exceções que as operações poderiam causar e verificar nos testes unitários. Pensando nas estruturas de dados, assumi algumas questões como: 1) em controle de ativos, assumi que as operações de “get” sejam mais frequentes que adição, utilizei ArrayList pois a complexidade é O(1) para get e add O (n); 2) utilizei uma estrutura de Hashtable para conseguir acessar um ativo por nome String em tempo O(1); 3) na classe Movimentação supondo que add seja mais frequente que get, utilizei LinkedList pois o add é O(1). Usei uma hashtable em movimentação também para pegar a quantidade de ativos por nome em tempo O(1). 

Utilizei uma classe Enum para tratar o Tipo de ativo e evitar passar um valor incorreto. Criei um teste Caso de Uso que simula e imprime todos os registros criados para verificar o sistema como um todo. 

Como estamos tratando de valores monetários me preocupei em implementar nos cálculos o BigDecimal para maximizar na medida do possível a precisão do calculo, uma vez que por exemplo, ao calcular 34.19f multiplicado por 10 em float ou double ele daria um valor aproximado e não exato.

![Corretora](https://github.com/boscocp/CorretoraDesafio/blob/master/Imagem/Corretora.png)


