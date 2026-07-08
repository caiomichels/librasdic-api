package br.udesc.librasdic_api.controller.subject.dto;

import br.udesc.librasdic_api.entity.Origin;
import br.udesc.librasdic_api.entity.Subject;
import br.udesc.librasdic_api.entity.Variation;

public record SubjectResponse(Long id, String name, String description) {
  
  public SubjectResponse(Subject subject) {
    this(subject.getId(), subject.getName(), subject.getDescription());
  }
}
