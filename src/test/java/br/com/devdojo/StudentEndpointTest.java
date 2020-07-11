package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import br.com.devdojo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private UserRepository userRepository;

    @TestConfiguration
    static class Config {
        public RestTemplateBuilder restTemplateBuilder(){
            return new RestTemplateBuilder().basicAuthentication("hongjoong", "devdojo");
        }
    }

    @Test
    public void whenLisStudentUsingIncorrectUserNameAndPassword_thenReturnStatusCode401Unauthorized(){
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/v3/protected/students/", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenGetStudentByIdUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized(){
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/v3/protected/students/1", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenFindStudentByNameUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized(){
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/v3/protected/students/findByName/studentName", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);

    }

    @Test
    public void whenSaveStudentUsingIncorrectUsernameAndPassword_thenReturnResourceAccessException(){
        Student student = new Student(1L, "Lalisa Manoban", "lisa.manoban@bighit.com");
        assertThrows(ResourceAccessException.class,
                () -> restTemplate.postForEntity("/v3/admin/students/", student, String.class));

    }

    @Test
    public void whenDeleteStudentsUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized(){
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.exchange("/v3/admin/students/{id}", HttpMethod.DELETE, null, String.class, 2L);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenUpdateStudentUsingIncorrectUsernameAndPassword_thenReturnResourceAccessException(){
        restTemplate = restTemplate.withBasicAuth("1", "1");
        Student student = new Student(1L, "Lalisa Manoban", "lisa.manoban@bighit.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Student> entity = new HttpEntity<Student>(student, headers);

        assertThrows(
                ResourceAccessException.class,
                () -> restTemplate.exchange("http://localhost:8080/v3/admin/students", HttpMethod.PUT, entity, String.class, 1L)
        );
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void whenListAllStudentUsingCorrectRole_thenReturnStatusCode200() throws Exception{
        List<Student> students = asList(new Student(1L, "Lalisa Manoban", "lisa.manoban@bighit.com"),
               new Student(2L, "Kim Jisoo", "kim.jisoo@bighit.com")

        );

        Page<Student> pagedStudents = new PageImpl(students);

        when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

        mockMvc.perform(get("http://localhost:8080/v3/protected/students/"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(studentRepository).findAll(isA(Pageable.class));
    }

    @Test
    @WithMockUser(username = "xx", password = "xx", roles = "USER")
    public void whenListAllStudentsUsingCorrectRole_thenReturnCorrectData() throws Exception {
        List<Student> students = asList(new Student(1L, "Lalisa Manoban", "lisa.manoban@bighit.com"),
                new Student(2L, "Kim Jisoo", "kim.jisoo@bighit.com")

        );

        Page<Student> pagedStudents = new PageImpl(students);

        when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

        mockMvc.perform(get("http://localhost:8080/v3/protected/students/"))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].name").value("Lalisa Manoban"))
                .andExpect(jsonPath("$.content[0].email").value("lisa.manoban@bighit.com"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].name").value("Kim Jisoo"))
                .andExpect(jsonPath("$.content[1].email").value("kim.jisoo@bighit.com"));

        verify(studentRepository).findAll(isA(Pageable.class));

    }




}
