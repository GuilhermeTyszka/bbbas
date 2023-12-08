KAFKA:

Inicialmente, ele era um sistema interno desenvolvido pela LinkedIn para processar 1,4 trilhão de mensagens por dia

https://blog.dp6.com.br/apache-kafka-o-que-é-e-como-funciona-300a5736e388

Inicialmente, o Kafka conseguia lidar 1,4 trilhões de mensagens por dia. Após Fevereiro de 2020, ele processou 7 trilhões de mensagens e mais de 5 petabytes de dados consumidos por dia, segundo a Confluent, empresa fundada pelos criadores de Apache Kafka e que mais contribui com a ferramenta.

O Apache Kafka é composto por alguns componentes principais:
* 		Mensagens: Um dado (ou evento) no Kafka é chamado de mensagem e um conjunto de mensagens é chamado de lote (ou batch);
* 		Tópicos: Local onde as mensagens são armazenadas;
* 		Partições: Os tópicos são divididos em partições;
* 		Segmentos de log: As mensagens do Kafka são escritas em arquivos de logs dentro das partições;
* 		Broker: É o servidor do Kafka, responsável por receber as mensagens dos producers, escrever as mensagens no disco e disponibilizar para os consumers;
* 		Producer: Serviço(s) responsável(eis) por enviar enviar as mensagens para o Kafka;
* 		Consumer: Serviço(s) responsável(eis) por ler as mensagens do Kafka.


FLINK:

O Apache Flink foi projetado para processamento de baixa latência, execução de cálculos na memória, alta disponibilidade, remoção de pontos únicos de falhas e aumento da escala na horizontal.

Por que usar o Apache Flink?
O Apache Flink é usado para criar muitos tipos diferentes de aplicações de transmissão e em lotes, devido ao amplo conjunto de atributos. Alguns dos tipos comuns de aplicações desenvolvidas pelo Apache Flink são:
* Aplicações orientadas por eventos, ingerindo eventos de um ou mais fluxos de eventos e executando cálculos, atualizações de estado ou ações externas. O processamento com estado permite implementar uma lógica além da transformação de mensagem única, em que os resultados dependem do histórico de eventos ingeridos.
* Aplicações de análise de dados, extraindo informações e insights dos dados. Tradicionalmente executada consultando conjuntos de dados finitos e reexecutando as consultas ou alterando os resultados para incorporar novos dados. Com o Apache Flink, a análise pode ser executada atualizando continuamente, transmitindo consultas ou processando eventos ingeridos em tempo real, emitindo e atualizando os resultados de forma constante.
* Aplicações de pipelines de dados, transformando e enriquecendo os dados a serem movidos de um armazenamento de dados para outro. Tradicionalmente, extract-transform-load (ETL) é executado periodicamente, em lotes. Com o Apache Flink, o processo pode operar continuamente, movendo os dados com baixa latência para o destino.

Como funciona o Apache Flink?
O Flink é um mecanismo de processamento de fluxo de alto throughput e baixa latência. Uma aplicação do Flink consiste em um gráfico de fluxo de dados acíclico complexo e arbitrário, composto por fluxos e transformações. Os dados são ingeridos de uma ou mais fontes de dados e enviados para um ou mais destinos. Os sistemas de fonte e destino podem ser fluxos, filas de mensagens ou datastores e incluem arquivos, bancos de dados populares e mecanismos de busca. As transformações podem ser com estado, como agregações ao longo de janelas de tempo ou detecção de padrões complexos. 
A tolerância a falhas é obtida por dois mecanismos separados: pontos de verificação automática e periódica do estado da aplicação, copiados para um armazenamento persistente, para permitir a recuperação automática em caso de falha; pontos de salvamento sob demanda, salvando uma imagem consistente do estado de execução, para permitir a pausa e retomada, atualizar ou bifurcar sua tarefa do Flink, mantendo o estado da aplicação em todas as pausas e reinicializações. Os mecanismos de ponto de verificação e ponto de salvamento são assíncronos, capturando um snapshot consistente do estado sem “parar o mundo”, enquanto a aplicação continua processando eventos.

https://aws.amazon.com/pt/what-is/apache-flink/

Quais são os casos de uso do Apache Flink?
Os casos de uso do Apache Flink incluem:
* Detecção de fraudes, detecção de anomalias, alertas baseados em regras e personalização de UX em tempo real são exemplos de casos de uso de aplicações orientadas por eventos. O Flink é perfeito para todos os casos de uso que exigem o processamento de fluxos de eventos com estado, considerando a evolução ao longo do tempo, detectando padrões complexos ou calculando estatísticas ao longo de janelas de tempo para detectar desvios dos limites esperados.
* Monitoramento de qualidade, análise ad-hoc de dados em tempo real, análise de clickstreams e avaliação de experimentos de produtos são casos de uso de análise de streaming que o Flink pode oferecer suporte eficiente. Aproveitando o alto nível de abstração da interface de programação de SQL ou API Table, é possível executar a mesma análise em streaming de dados em tempo real e em lotes de dados históricos.
* Monitorar o sistema de arquivos e gravar dados em um log, materializar um fluxo de eventos em um banco de dados, e criar e refinar incrementalmente um índice de pesquisa são casos de uso com suporte eficiente pelo ETL contínuo. Aproveitando o amplo conjunto de conectores, o Flink pode ler diretamente de vários tipos de armazenamentos de dados, ingerir fluxos de eventos de alteração e até mesmo capturar alterações diretamente. Com a ingestão e o processamento contínuos das alterações e a atualização direta dos sistemas de destino, o Flink pode reduzir o atraso da sincronização de dados para alguns segundos ou menos.

KUDU
O que é o Apache Kudu?
Apache Kudu é um sistema de armazenamento em formato de colunas gratuito e de código aberto desenvolvido para o Apache Hadoop. É um mecanismo destinado a dados estruturados compatível com acesso aleatório de baixa latência em escala de milissegundos a linhas individuais, juntamente com ótimos padrões de acesso analítico. É um mecanismo de big data criado para estabelecer a conexão entre o Hadoop Distributed File System [HDFS] amplamente disseminado e o banco de dados HBase NoSQL.

De acordo com a definição do site oficial Apache Kudu, é um complemento ao ecossistema Hadoop que completa a camada de armazenamento do HDFS e proporciona análise rápida para dados dados rápidos.

Diferentes de outros motores como Avro e Parquet o Apache Kudu veio solucionar problemas para a arquitetura de streaming onde os dados mudam rapidamente e onde também é necessária a análise rápida desses dados.

https://bigdatalikeaboss.wordpress.com/2018/08/21/apache-kudu-introducao-parte-1/

https://kudu.apache.org

Kudu’s benefits include:
* 		Fast processing of OLAP workloads;
* 		Integration with MapReduce, Spark, Flume, and other Hadoop ecosystem components;
* 		Tight integration with Apache Impala, making it a good, mutable alternative to using HDFS with Apache Parquet;
* 		Strong but flexible consistency model, allowing you to choose consistency requirements on a per-request basis, including the option for strict serialized consistency;
* 		Strong performance for running sequential and random workloads simultaneously;
* 		Structured data model.
