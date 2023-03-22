package com.mysite.sbb.answer.entity;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.question.entity.Question;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Question question;
    @Column(columnDefinition = "TEXT")
    private String content;
    @CreatedDate
    private LocalDateTime createDate;
    @CreatedDate
    private LocalDateTime modifyDate;
    @ManyToOne
    private Member author;
}
