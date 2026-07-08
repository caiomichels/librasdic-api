package br.udesc.librasdic_api.entity;

import br.udesc.librasdic_api.controller.variation.dto.VariationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "variation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Variation {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column()
  private String description;

  public Variation(VariationRequest request) {
    name = request.name();
    description = request.description();
  }

  public Variation(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
