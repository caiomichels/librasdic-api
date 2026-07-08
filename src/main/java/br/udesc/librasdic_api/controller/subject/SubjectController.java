package br.udesc.librasdic_api.controller.subject;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.udesc.librasdic_api.repository.SubjectRepository;
import br.udesc.librasdic_api.controller.subject.dto.SubjectResponse;
import br.udesc.librasdic_api.entity.Subject;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

  @Autowired
  private SubjectRepository repository;

  @GetMapping("/all")
  public ResponseEntity<Iterable<SubjectResponse>> getAll() {
    return ResponseEntity.ok(repository.findAll().stream().map(SubjectResponse::new).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubjectResponse> get(@PathVariable Long id) {
    Optional<Subject> subject = repository.findById(id);

    if (subject.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(new SubjectResponse(subject.get()));
  }
}
