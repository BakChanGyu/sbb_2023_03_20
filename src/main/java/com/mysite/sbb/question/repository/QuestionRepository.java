package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);

    Question findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable);
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
    @Modifying // 만약 아래 쿼리가 select 이외라면 붙여줘야함.
    @Transactional
    // nativeQuery = true여야 mysql 쿼리문법 사용가능.
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1",
            nativeQuery = true)
    void clearIncrement();

    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join Member m1 on q.author=m1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join Member m2 on q.author=m2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or m1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or m2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
