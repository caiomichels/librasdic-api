package br.udesc.librasdic_api.controller.word.dto;

import br.udesc.librasdic_api.controller.origin.dto.OriginResponse;
import br.udesc.librasdic_api.controller.sign.dto.SignResponse;
import br.udesc.librasdic_api.controller.variation.dto.VariationResponse;
import br.udesc.librasdic_api.entity.Sign;
import br.udesc.librasdic_api.entity.Word;

public record WordResponse(Long id, String word, String example, String librasExample, String imageUrl, SignResponse mainSign, SubjectResponse subject, GramaticalClassResponse gramClass) {
  
  public WordResponse(Word word) {
    this(word.getId(), word.getWord(), word.getExample(), word.getLibrasExample(), word.getImageUrl(), word.getMainSign() != null ? new SignResponse(word.getMainSign()) : null, new SubjectResponse(word.getSubject()), new GramaticalClassResponse(word.getGramClass()));
  }
}
