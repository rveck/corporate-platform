# Corporate Platform
O objetivo dessa aplicação é permitir o cadastro de clientes (nome e idade) e a partir do IP da requisição consultar a localização GPS e previsão do tempo do dia e armazemá-los na base.
As ferramentas utilizadas no desenvolvimento foram escolhidas para permitir que haja um escalonamento horizontal.
Recomenda-se a configuração do deploy de produção no Jenkins.

# Frameworks utilizados:
- [x] Spring Boot
- [x] Spring Cloud Config
- [x] Spring Data MongoDB
- [x] Spring Eureka
- [x] Spring Zuul
- [x] Spring AMQP
- [x] Spring Eureka
- [x] Mockito

# Diagrama Macro:
![corporate_platform](https://user-images.githubusercontent.com/10779649/51645212-554ae480-1f5a-11e9-8466-a0dbb7e8a079.jpg)

# Pré Requisitos para Montar o Ambiente
1. Ter o Docker instalado para rodar a imagem do RabbitMQ (arquivo docker-composer.yml) ou ter o RabbitMQ server instalado e rodando nas portas padrão;
2. Ter o MongoDB instalado sem o modo de autenticação configurado;

# Como usar os serviços
1. Faça o checkout dos projetos;
2. Build os projetos com o comando `mvn clean install` começando com os projetos "document" e "utility";
3. Rode as aplicações com o comando `java -jar <NOME DO JAR NA PASTA TARGET>` começando com os projetos "config-service" e "eureka-service";
4. URLs disponíveis:
- Criar cliente (POST): http://localhost:9999/api/customer/; Request: {"name":"José da Silva","age":42}
- Atualizar cliente (PUT): http://localhost:9999/api/customer/; Request: {"id": "< ID DO CLIENTE >", "name":"José da Silva","age":42}
- Deletar Cliente (DELETE): http://localhost:9999/api/customer/< ID DO CLIENTE >
- Consultar todos os clientes cadastrados (GET): http://localhost:9999/customer-service/customer/
- Consultar os dados de um cliente pelo ID (GET): http://localhost:9999/customer-service/customer/< ID DO CLIENTE >

# Observações
1. Caso seja necessário alterar alguma configuração do projeto, faça o checkout do projeto https://github.com/rveck/corporate-platform-config, altere as configurações necessários, faça o push em um outro repositório GIT e indique o camainho do novo repositoório no arquivo "boostrap.properties" e todos os projetos;
2. Se o IP capturado no request for 0:0:0:0:0:0:0:1, a aplicação não tentará consultar a localização GPS e previsão do tempo;
3. No arquivo CustomerService.java contem as configurações para consulta da geolocalização e clima. Caso não haja previsões para o dia atual, pode-se alterar descomentar outros valores para as constantes WEATHER_DATE_FIELD e seu "SimpleDateFormat" correspondente.
