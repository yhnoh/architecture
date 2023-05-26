package com.example.cleanarchitecture.layered_architecture.member.service;

import com.example.cleanarchitecture.layered_architecture.member.dto.MemberDTO;
import com.example.cleanarchitecture.layered_architecture.member.entity.Member;
import com.example.cleanarchitecture.layered_architecture.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void join(MemberDTO memberDTO){

        //DAO에 직접 접근하여 저장까지 진행
        Member member = new Member(memberDTO.getUsername());
        memberJpaRepository.save(member);
    }
}
