package br.com.devdojo.javaclient;

import br.com.devdojo.handler.RestResponseExceptHandler;
import br.com.devdojo.model.PageableResponse;
import br.com.devdojo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaClientDAO {
    RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v3/protected/students")
            .basicAuthentication("hongjoong", "devdojo")
            .errorHandler(new RestResponseExceptHandler())
            .build();

    RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v3/admin/students")
            .basicAuthentication("hongjoong", "devdojo")
            .errorHandler(new RestResponseExceptHandler())
            .build();

    public Student findById(long id){
        return restTemplate.getForObject("/{id}", Student.class, id);
//        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 23);
    }

    public List<Student> listAll(){
        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Student>>() {});
        return exchange.getBody().getContent();
    }

    public Student save(Student student){
        ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange("/", HttpMethod.POST,
                new HttpEntity<>(student, createJSONHeader()), Student.class);
        return exchangePost.getBody();
    }
    
    public void update(Student student){
        restTemplateAdmin.put("/", student);
    }

    public void delete(Long id){
        restTemplateAdmin.delete("/{id}", id);
    }

    private static HttpHeaders createJSONHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
