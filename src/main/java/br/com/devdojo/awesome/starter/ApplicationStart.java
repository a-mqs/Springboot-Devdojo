package br.com.devdojo.awesome.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * ? Essa é a classe que starta o Spring Boot.
 * ? Diferente de projetos web, onde você tem que
 * ? startar o servidor, aqui o Spring Boot é
 * ? quem se encarrega de fazer isso.
 */

@EnableAutoConfiguration
@ComponentScan(basePackages = "br.com.devdojo.awesome.endpoint")
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
