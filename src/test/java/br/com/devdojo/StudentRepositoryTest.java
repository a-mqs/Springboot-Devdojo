package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void whenCreate_thenPersistData(){
        // Arrange (A preparação do contexto)
        Student student = new Student("Taemin", "taemin.teste@email.com");
        // Act (A ação que se quer testar)
        this.studentRepository.save(student);
        // Assert (Comparar os resultados)
        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Taemin");
        assertThat(student.getEmail()).isEqualTo("taemin.teste@email.com");

    }

    @Test
    public void whenDelete_thenRemoveData(){
        Student student = new Student("Taemin", "tamin.teste@email.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);
        assertThat(studentRepository.findById(student.getId())).isEmpty();
    }

    @Test
    public void whenUpdate_thenChangeAndPersistData(){
        Student student = new Student("Taemin", "taemin.teste@email.com");
        this.studentRepository.save(student);

        student = student = new Student("Taemin2", "taemin2.teste@email.com");
        this.studentRepository.save(student);

        student = studentRepository.findById(student.getId()).orElse(null);

        assertThat(student.getName()).isEqualTo("Taemin2");
        assertThat(student.getEmail()).isEqualTo("taemin2.teste@email.com");

    }

    @Test
    public void whenFindByNameIgnoreCaseContaining_thenIgnoreCase(){
        Student student1 = new Student("Taemin", "tamin.teste@email.com");
        Student student2 = new Student("taemin", "tamin.teste2@email.com");

        this.studentRepository.save(student1);
        this.studentRepository.save(student2);

        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("taemin");

        assertThat(studentList.size()).isEqualTo(2);

    }

    @Test
    public void whenNotEmptyName_thenNoConstraintViolations(){
        Exception exception = assertThrows(
                ConstraintViolationException.class,
                () -> studentRepository.save(new Student("", "taemin.teste@email.com"))
        );

        assertTrue(exception.getMessage().contains("O campo nome do estudante é obrigatório"));
    }

    @Test
    public void whenNotEmptyEmail_thenNoConstraintViolations(){
        Exception exception = assertThrows(
                ConstraintViolationException.class,
                () -> studentRepository.save(new Student("Taemin", ""))
        );

        assertTrue(exception.getMessage().contains("O campo email é obrigatório"));
    }

    @Test
    public void whenValidEmail_thenNoConstraintViolations(){
        Exception exception = assertThrows(
                ConstraintViolationException.class,
                () -> studentRepository.save(new Student("Taemin", "wrongemail.com"))
        );

        assertTrue(exception.getMessage().contains("O email deve ser válido"));
    }





}
