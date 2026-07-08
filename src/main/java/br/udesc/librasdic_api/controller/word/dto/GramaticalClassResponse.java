package br.udesc.librasdic_api.controller.word.dto;

import br.udesc.librasdic_api.entity.GramaticalClass;
import br.udesc.librasdic_api.entity.Origin;
import br.udesc.librasdic_api.entity.Variation;

public record GramaticalClassResponse(Long id, String name) {
  
  public GramaticalClassResponse(GramaticalClass gramClass) {
    this(gramClass.getId(), gramClass.getName());
  }
}
