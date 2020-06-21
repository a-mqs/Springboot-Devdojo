package br.com.devdojo.repository;

import br.com.devdojo.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Interface para diminuir significantemente a quantidade de código
 * requerida para implementar os padrões de data access (métodos que
 * compõem os métodos HTTP)
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
     List<Student> findByNameIgnoreCaseContaining(String name);
}
