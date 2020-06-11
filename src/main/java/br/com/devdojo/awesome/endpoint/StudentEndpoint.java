package br.com.devdojo.awesome.endpoint;

import br.com.devdojo.awesome.model.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * ? Essa é a classe que define o ponto final de
 * ? até onde a API é acessada, nela contém os principais
 * ? métodos.
 */

@RestController
@RequestMapping("students")
public class StudentEndpoint {

    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public List<Student> listAll(){
        return asList(new Student("Kim Namjoon"),
                new Student("Kim Seokjin")
        );
    }

}
