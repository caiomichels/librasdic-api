package br.udesc.librasdic_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.udesc.librasdic_api.controller.word.dto.WordSimpleResponse;
import br.udesc.librasdic_api.entity.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

  List<WordSimpleResponse> findByWordStartingWithIgnoreCase(String prefix);
  List<WordSimpleResponse> findByWordContainingIgnoreCase(String text);
  List<WordSimpleResponse> findBySubject_NameContainingIgnoreCase(String text);
  List<WordSimpleResponse> findByExampleContainingIgnoreCase(String text);
  List<WordSimpleResponse> findByDescriptionContainingIgnoreCase(String text);
  List<WordSimpleResponse> findBySubject_Id(Long id);
}
