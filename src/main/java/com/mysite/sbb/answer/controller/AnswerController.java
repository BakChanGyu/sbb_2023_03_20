package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.Service.AnswerService;
import com.mysite.sbb.answer.dto.AnswerForm;
import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sbb/answer")
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createAnswer(Model model, @PathVariable("id") long id,
                               @Valid AnswerForm answerForm,
                               BindingResult bindingResult,
                               Principal principal) {
        Question question = questionService.getQuestion(id);
        Member member = memberService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        Answer answer = answerService.create(question, answerForm.getContent(), member);
        return String.format("redirect:/sbb/question/detail/%s", answer.getQuestion().getId(), answer.getId());
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Long id,
                               Principal principal) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerModify(@Valid AnswerForm answerForm,
                               BindingResult bindingResult,
                               @PathVariable("id") Long id,
                               Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/sbb/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal,
                               @PathVariable("id") Long id) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        answerService.delete(answer);
        return String.format("redirect:/sbb/question/detail/%s", answer.getQuestion().getId());
    }

    @GetMapping("/vote/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerVote(Principal principal, @PathVariable("id") Long id) {
        Answer answer = answerService.getAnswer(id);
        Member member = memberService.getMember(principal.getName());
        answerService.vote(answer, member);
        return String.format("redirect:/sbb/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId());
    }
}
