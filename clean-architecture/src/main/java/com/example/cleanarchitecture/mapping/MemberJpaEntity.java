package com.example.cleanarchitecture.mapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @OneToMany
    private List<TeamJpaEntity> teams = new ArrayList<>();
}
