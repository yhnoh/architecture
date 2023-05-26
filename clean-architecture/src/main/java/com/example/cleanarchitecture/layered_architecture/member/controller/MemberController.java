package com.example.cleanarchitecture.layered_architecture.member.controller;

import com.example.cleanarchitecture.layered_architecture.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

}
