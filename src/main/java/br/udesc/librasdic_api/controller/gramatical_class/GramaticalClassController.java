package br.udesc.librasdic_api.controller.gramatical_class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.udesc.librasdic_api.repository.GramaticalClassRepository;
import br.udesc.librasdic_api.entity.GramaticalClass;

@RestController
@RequestMapping("/api/class")
public class GramaticalClassController {

  @Autowired
  private GramaticalClassRepository repository;

  @GetMapping("/all")
  public ResponseEntity<Iterable<GramaticalClass>> getAll() {
    return ResponseEntity.ok(repository.findAll());
  }
}
