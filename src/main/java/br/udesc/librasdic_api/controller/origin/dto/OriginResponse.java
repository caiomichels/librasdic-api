package br.udesc.librasdic_api.controller.origin.dto;

import br.udesc.librasdic_api.entity.Origin;

public record OriginResponse(Long id, String name) {
  
  public OriginResponse(Origin origin) {
    this(origin.getId(), origin.getName());
  }
}
