package br.com.devdojo.awesome.endpoint;

import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.awesome.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    @Autowired
    private DateUtil dateUtil;

    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public List<Student> listAll(){
        System.out.println("Data e hora da request: " + dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        List<Student> studentList = asList(new Student("Kim Namjoon"),
                new Student("Kim Seokjin")
        );
        System.out.println("Data e hora da response: " + dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return studentList;
    }

}
