package br.udesc.librasdic_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sign")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sign {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "video_url", nullable = false)
  private String videoUrl;

  @ManyToOne(optional = false)
  @JoinColumn(name = "word_id")
  private Word word;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "variation_id")
  private Variation variation;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "origin_id")
  private Origin origin;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "hand_id")
  private Hand hand;

  public Sign(String videoUrl, Word word, Variation variation, Origin origin, Hand hand) {
    this.videoUrl = videoUrl;
    this.word = word;
    this.variation = variation;
    this.origin = origin;
    this.hand = hand;
  }
}
