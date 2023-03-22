package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);

    Question findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable);

    @Modifying // 만약 아래 쿼리가 select 이외라면 붙여줘야함.
    @Transactional
    // nativeQuery = true여야 mysql 쿼리문법 사용가능.
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1",
            nativeQuery = true)
    void clearIncrement();
}
