package br.com.devdojo.endpoint;

import br.com.devdojo.error.ResourceNotFoundException;
import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Classe que define o ponto final até onde essa aplicação
 * (ou uma outra API) é acessada, nela contém os principais
 * métodos HTTP para acesso dos recursos.
 */


@RestController
@RequestMapping("v3")
public class StudentEndpoint {
    private final StudentRepository studentDAO;

    @Autowired
    public StudentEndpoint(StudentRepository studentDAO){
        this.studentDAO = studentDAO;
    }

    @GetMapping(path = "protected/students")
    public ResponseEntity<?> listAll(Pageable pageable){
        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id,
                                            @AuthenticationPrincipal UserDetails userDetails){
        System.out.println(userDetails);
        verifyIfStudentExists(id);
        Student student = studentDAO.findById(id).get();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/findByName/{name}")
    public ResponseEntity<?> findStudentByName(@PathVariable String name){
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping(path = "admin/students")
    public ResponseEntity<?> save(@Valid @RequestBody Student student){
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }


    @DeleteMapping(path = "admin/students/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        verifyIfStudentExists(id);
        studentDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "admin/students")
    public ResponseEntity<?> update(@RequestBody Student student){
        verifyIfStudentExists(student.getId());
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id){
        if (!studentDAO.findById(id).isPresent()){
            throw new ResourceNotFoundException("Student not found for id: " + id);
        }
    }

}
