package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.ClubActivity;

import java.util.List;

public interface ClubActivityService {

    ClubActivity findClubActivityById(Long clubactivityid);

    List<ClubActivity> findAll();
}
