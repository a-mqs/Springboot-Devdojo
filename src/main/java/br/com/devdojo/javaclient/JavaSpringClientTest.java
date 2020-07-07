package br.com.devdojo.javaclient;

import br.com.devdojo.model.Student;

import java.util.List;

public class JavaSpringClientTest {
    public static void main(String[] args) {

        Student studentPost = new Student();
        studentPost.setName("Min Yoongi");
        studentPost.setEmail("min.yoongi4@bighit.com");
        JavaClientDAO dao = new JavaClientDAO();
//        System.out.println(dao.findById(7));
//        System.out.println(dao.listAll());
//        System.out.println(dao.save(studentPost));
        studentPost.setId(28L);
        dao.update(studentPost);
        dao.delete(28L);

    }
}
