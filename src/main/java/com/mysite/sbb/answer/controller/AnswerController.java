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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        answerService.create(question, answerForm.getContent(), member);
        return String.format("redirect:/sbb/question/detail/%s", id);
    }
}
