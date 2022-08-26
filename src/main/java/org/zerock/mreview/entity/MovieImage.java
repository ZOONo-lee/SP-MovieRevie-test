package org.zerock.mreview.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "movie")
@Getter
public class MovieImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long inum;

  private String uuid;
  private String imgName;
  private String path;
  @ManyToOne(fetch = FetchType.LAZY)
  private Movie movie;
}
