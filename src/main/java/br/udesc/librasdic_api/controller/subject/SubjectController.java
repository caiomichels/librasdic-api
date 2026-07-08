package br.udesc.librasdic_api.controller.subject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.udesc.librasdic_api.repository.SubjectRepository;
import br.udesc.librasdic_api.entity.Subject;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

  @Autowired
  private SubjectRepository repository;

  @GetMapping("/all")
  public ResponseEntity<Iterable<Subject>> getAll() {
    return ResponseEntity.ok(repository.findAll());
  }
}
