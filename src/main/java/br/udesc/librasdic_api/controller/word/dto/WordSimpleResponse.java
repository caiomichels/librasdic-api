package br.udesc.librasdic_api.controller.word.dto;

import br.udesc.librasdic_api.controller.origin.dto.OriginResponse;
import br.udesc.librasdic_api.controller.variation.dto.VariationResponse;
import br.udesc.librasdic_api.entity.Sign;
import br.udesc.librasdic_api.entity.Word;

public record WordSimpleResponse(Long id, String word) {
  
  public WordSimpleResponse(Word word) {
    this(word.getId(), word.getWord());
  }
}
