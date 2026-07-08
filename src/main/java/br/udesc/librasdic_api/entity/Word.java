package br.udesc.librasdic_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "word")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Word {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String word;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private String example;
  @Column(name = "libras_example", nullable = false)
  private String librasExample;
  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "subject_id")
  private Subject subject;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "class_id")
  private GramaticalClass gramClass;

  @OneToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "main_sign_id")
  private Sign mainSign;

  public Word(String word, String description, String example, String librasExample, String imageUrl, Subject subject, GramaticalClass gramClass) {
    this.word = word;
    this.description = description;
    this.example = example;
    this.librasExample = librasExample;
    this.imageUrl = imageUrl;
    this.subject = subject;
    this.gramClass = gramClass;
  }
}
