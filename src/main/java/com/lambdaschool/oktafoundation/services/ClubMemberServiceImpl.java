package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service(value = "clubMembersService")
public class ClubMemberServiceImpl implements ClubMemberService{

    @Autowired
    private ClubMembersRepository clubMembersRepo;

    @Autowired
    private ReactionRepository reactionrepos;

    @Autowired
    private ReactionService reactionService;


    @Override
    public List<ClubMembers> findAll() {
        List<ClubMembers> clubMembersList = new ArrayList<>();

        clubMembersRepo.findAll()
                .iterator()
                .forEachRemaining(clubMembersList::add);
        return clubMembersList;
    }

    @Override
    public void deleteAll() {
        clubMembersRepo.deleteAll();
    }

    @Override
    public ClubMembers save(ClubMembers clubMember) {

        return clubMembersRepo.save(clubMember);
    }
}
