package br.udesc.librasdic_api.controller.hand.dto;

import br.udesc.librasdic_api.entity.Hand;

public record HandResponse(Long id, String name, String imageUrl) {
  
  public HandResponse(Hand hand) {
    this(hand.getId(), hand.getName(), hand.getImageUrl());
  }
}
