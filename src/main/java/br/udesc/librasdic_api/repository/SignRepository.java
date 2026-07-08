package br.udesc.librasdic_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.udesc.librasdic_api.entity.Sign;
import br.udesc.librasdic_api.entity.Word;

@Repository
public interface SignRepository extends JpaRepository<Sign, Long> {

  List<Sign> findByWord(Word word);
  List<Sign> findByWord_Id(Long id);
}
