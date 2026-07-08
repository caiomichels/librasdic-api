package br.udesc.librasdic_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.udesc.librasdic_api.entity.Hand;

@Repository
public interface HandRepository extends JpaRepository<Hand, Long> {
}
