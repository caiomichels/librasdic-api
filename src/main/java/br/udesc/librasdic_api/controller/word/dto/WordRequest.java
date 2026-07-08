package br.udesc.librasdic_api.controller.word.dto;

public record WordRequest(String word, String description, String example, String librasExample, Long subjectId, Long gramClassId) {
}
