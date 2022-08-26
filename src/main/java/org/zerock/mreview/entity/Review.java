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
@ToString(exclude = {"movie","member"})
@Getter

public class Review extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewnum;

  @ManyToOne(fetch = FetchType.LAZY)
  private Movie movie;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;
  private int grade;
  private String text;

  public void changeGrade(int grade) {
    this.grade = grade;
  }
  public void changeText(String text){
    this.text = text;
  }
}
