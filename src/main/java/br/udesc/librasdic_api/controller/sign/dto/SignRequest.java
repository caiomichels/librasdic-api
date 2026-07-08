package br.udesc.librasdic_api.controller.sign.dto;

import br.udesc.librasdic_api.controller.variation.dto.VariationRequest;

public record SignRequest(Long wordId, Long handId, VariationRequest variation, Long originId) {
}

