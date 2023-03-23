package com.mysite.sbb.question.entity;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    @CreatedDate
    private LocalDateTime createDate;
    @CreatedDate
    private LocalDateTime modifyDate;
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    // OneToMany 에는 직접객체 초기화 << 수업못들음.
    private List<Answer> answerList;
    @ManyToOne
    private Member author;
    @ManyToMany
    Set<Member> voter;
}
