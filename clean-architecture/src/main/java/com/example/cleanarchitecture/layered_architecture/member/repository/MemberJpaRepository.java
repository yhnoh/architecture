package com.example.cleanarchitecture.layered_architecture.member.repository;

import com.example.cleanarchitecture.layered_architecture.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
