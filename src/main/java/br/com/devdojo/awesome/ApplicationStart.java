package br.com.devdojo.awesome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ? Essa é a classe que starta o Spring Boot.
 * ? Diferente de projetos web, onde você tem que
 * ? startar o servidor, aqui o Spring Boot é
 * ? quem se encarrega de fazer isso.
 */

/**
 * ! Starter retirado do seu próprio pacote e colocado
 * ! no pacote geral (awesome) para que pudesse abranger
 * ! todas as outras classes. Isso anula a necessidade
 * ! do base package.
 */


@SpringBootApplication
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
