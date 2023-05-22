package com.example.cleanarchitecture.mapping;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private Long id;
    private String username;
    private List<Team> teams = new ArrayList<>();
}
