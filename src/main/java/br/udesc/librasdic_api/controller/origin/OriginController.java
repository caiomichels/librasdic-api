package br.udesc.librasdic_api.controller.origin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.udesc.librasdic_api.entity.Origin;
import br.udesc.librasdic_api.repository.OriginRepository;

@RestController
@RequestMapping("/api/origin")
public class OriginController {

  @Autowired
  private OriginRepository repository;

  @GetMapping("/all")
  public ResponseEntity<Iterable<Origin>> getAll() {
    return ResponseEntity.ok(repository.findAll());
  }
}
