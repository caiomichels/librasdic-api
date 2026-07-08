package br.udesc.librasdic_api.controller.sign.dto;

import br.udesc.librasdic_api.controller.origin.dto.OriginResponse;
import br.udesc.librasdic_api.controller.variation.dto.VariationResponse;
import br.udesc.librasdic_api.entity.Sign;
import br.udesc.librasdic_api.entity.Word;

public record SignSimpleResponse(Long id, String variationName) {
  
  public SignSimpleResponse(Sign sign) {
    this(sign.getId(), sign.getVariation() != null ? sign.getVariation().getName() : "Padrão");
  }
}
