package br.udesc.librasdic_api.controller.variation.dto;

import br.udesc.librasdic_api.entity.Origin;
import br.udesc.librasdic_api.entity.Variation;

public record VariationResponse(Long id, String name, String description) {
  
  public VariationResponse(Variation variation) {
    this(variation.getId(), variation.getName(), variation.getDescription());
  }
}
