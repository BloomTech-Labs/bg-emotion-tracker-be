package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.ClubMembers;

import java.util.List;

public interface  ClubMemberService {
    List<ClubMembers> findAll();

    void deleteAll();

    ClubMembers save(ClubMembers clubMember);

}
