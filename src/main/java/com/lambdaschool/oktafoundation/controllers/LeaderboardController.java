package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.views.ClubSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/leaderboard")
public class Leaderboard {
    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubMembersRepository clubMembersRepository;

//  Highest Sentiment

//  Most improves
//    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/leaderboard")
    public ResponseEntity<?> getLeaderboard() {
        List<ClubSummary> rtnList = clubRepository.getClubsSummary();
        for(ClubSummary cs: rtnList) {
//            System.out.println(cs.getClubname());
        }
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

}
