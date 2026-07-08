package br.udesc.librasdic_api.controller.sign.dto;

import br.udesc.librasdic_api.controller.hand.dto.HandResponse;
import br.udesc.librasdic_api.controller.origin.dto.OriginResponse;
import br.udesc.librasdic_api.controller.variation.dto.VariationResponse;
import br.udesc.librasdic_api.entity.Sign;

public record SignResponse(Long id, Long wordId, String videoUrl, HandResponse hand, VariationResponse variation, OriginResponse origin) {
  
  public SignResponse(Sign sign) {
    this(sign.getId(), sign.getWord().getId(), sign.getVideoUrl(), new HandResponse(sign.getHand()), sign.getVariation() != null ? new VariationResponse(sign.getVariation()) : null, new OriginResponse(sign.getOrigin()));
  }
}
