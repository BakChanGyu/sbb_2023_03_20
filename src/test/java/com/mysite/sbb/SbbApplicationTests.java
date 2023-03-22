package com.mysite.sbb;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.repository.AnswerRepository;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import com.mysite.sbb.question.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerRepository answerRepository;

//	@BeforeEach // 아래 메서드는 각 테스트케이스가 실행되기 전에 실행된다.
	void beforeEach() {
		// 모든 데이터 삭제
		questionRepository.deleteAll();
		// 흔적삭제(다음번 insert때 id가 1번으로 설정되도록)
		questionRepository.clearIncrement();

		Question q1 = Question.builder()
				.subject("sbb가 무엇인가요?")
				.content("sbb에 대해서 알고 싶습니다.")
				.createDate(LocalDateTime.now())
				.modifyDate(LocalDateTime.now())
				.build();
		questionRepository.save(q1);

		Question q2 = Question.builder()
				.subject("스프링부트 모델 질문입니다.")
				.content("id는 자동으로 생성되나요?")
				.createDate(LocalDateTime.now())
				.modifyDate(LocalDateTime.now())
				.build();
		questionRepository.save(q2);
	}
	@Test
	void testJpa() {
		Question q1 = Question.builder()
				.subject("sbb가 무엇인가요?")
				.content("sbb에 대해서 알고 싶습니다.")
				.createDate(LocalDateTime.now())
				.modifyDate(LocalDateTime.now())
				.build();
		questionRepository.save(q1);

		Question q2 = Question.builder()
				.subject("스프링부트 모델 질문입니다.")
				.content("id는 자동으로 생성되나요?")
				.createDate(LocalDateTime.now())
				.modifyDate(LocalDateTime.now())
				.build();
		questionRepository.save(q2);

		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());

		Optional<Question> oq = questionRepository.findById(1L);
		if (oq.isPresent()) {
			Question question = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}

		Question subject = questionRepository.findBySubject("sbb가 무엇인가요?");
		long id = subject.getId();
		assertEquals(1, id);

		Question q3 = questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q3.getId());

		Question q4 = questionRepository.findBySubjectLike("sbb%");
		assertEquals("sbb에 대해서 알고 싶습니다.", q4.getContent());

		Optional<Question> q5 = questionRepository.findById(1L);
		assertTrue(q5.isPresent());
		if (q5.isPresent()) {
			Question question = q5.get();
			question.setSubject("수정된 제목");
			questionRepository.save(question);
		}

		assertEquals(2, questionRepository.count());
		Optional<Question> oq2 = questionRepository.findById(1L);
		assertTrue(oq2.isPresent());
		if (oq2.isPresent()) {
			Question question = oq2.get();
			questionRepository.delete(question);
			assertEquals(1, questionRepository.count());

		}
	}

	@Transactional
	@Test
	void testJpa2() {
		Question q1 = Question.builder()
				.subject("sbb가 무엇인가요?")
				.content("제곧내")
				.createDate(LocalDateTime.now())
				.modifyDate(LocalDateTime.now())
				.build();

		questionRepository.save(q1);

		Optional<Question> q1ById = questionRepository.findById(1L);
		assertTrue(q1ById.isPresent());

		if (q1ById.isPresent()) {
			Answer a1 = Answer.builder()
					.question(q1ById.get())
					.content("sbb는 이것입니다.")
					.createDate(LocalDateTime.now())
					.modifyDate(LocalDateTime.now())
					.build();
			answerRepository.save(a1);
		}

		Optional<Answer> a1byId = answerRepository.findById(1L);
		assertEquals("sbb는 이것입니다.", a1byId.get().getContent());
		assertEquals(1, a1byId.get().getQuestion().getId());

		Optional<Question> q2 = questionRepository.findById(1L);
		if (q2.isPresent()) {
			Question question = q2.get();

			assertEquals(1, question.getAnswerList().size());
			assertEquals("sbb는 이것입니다", question.getAnswerList().get(0).getContent());
		}
	}

	@Test
	void t3() {
		assertEquals(2, 2);
	}

	@Test
	void t4() {
		for (int i = 0; i < 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			questionService.create(subject, content);
		}
	}
}
