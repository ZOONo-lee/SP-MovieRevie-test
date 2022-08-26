package org.zerock.mreview.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;

@SpringBootTest
public class MemberRepositoryTests {
  @Autowired
  MemberRepository repository;

  @Test
  public void insertMembers(){
    IntStream.rangeClosed(1, 100).forEach(i->{
      Member member = Member.builder().email("r"+i+"@zerock.org")
                      .pw("1").nickname("reviewer"+i).build();
      repository.save(member);
    });
  }
}
