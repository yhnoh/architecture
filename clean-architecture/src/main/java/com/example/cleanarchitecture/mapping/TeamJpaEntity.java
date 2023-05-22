package com.example.cleanarchitecture.mapping;

import javax.persistence.*;

@Entity
public class TeamJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberJpaEntity member;
}
