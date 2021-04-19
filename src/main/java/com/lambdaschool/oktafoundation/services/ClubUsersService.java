package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.ClubUsers;

import java.util.List;

public interface ClubUsersService {
    List<ClubUsers> findAll();

    ClubUsers findClubUserById(Long clubuserid);
}
