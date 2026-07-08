package br.udesc.librasdic_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.udesc.librasdic_api.entity.Variation;

@Repository
public interface VariationRepository extends JpaRepository<Variation, Long> {
}
