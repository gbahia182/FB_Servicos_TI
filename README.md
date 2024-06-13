# FB_Servicos_TI
Sistema gerenciador de vendas e de estoque em Java(17) feito com Spring boot e com banco de dados H2.

1- Dê "RUN" no código
2- Pesquise localhost:8443/h2-console
3- Coloque no campo JDBC URL: jdbc:h2:~/db
4- Coloque no campo user: sgv
5- Coloque no campo password: 123  
6- Cole o seguinte comando na caixa de comandos: 

insert into Usuario (login,senha, papel, id) values
('admin','$2a$10$K6PG.YUsSpMT/LOyPpeB5eUVdPImfDfSH.N0xLHAC1NbgbIBhraHe','ADMIN',1);

7- Rode a instrução SQL;
8- No navegador, escreva o endereço: https://localhost:8443/.
9- Logue com o usuario admin e senha 123
10- Pronto! Sistema configurado!
