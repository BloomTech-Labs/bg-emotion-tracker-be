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
//        Member member = clubMember.getMember();
//        Club club = clubMember.getClub();
//        ClubMembers newClubMember = new ClubMembers(club, member);
//
//        if (member.getMember_table_id() != 0) {
//            clubMembersRepo.findById(member.getMember_table_id())
//                    .orElseThrow(() -> new ResourceNotFoundException("Member id " + member.getMember_table_id() + " not found!!"));
//            newClubMember.getMember().setMember_table_id(member.getMember_table_id());
//        }
//
////         if the memberid is already in the database, return it, no new member is created.
//        Optional<ClubMembers> temp = clubMembersRepo.findClubMembersByClub_ClubidAndMember_Memberid(club.getClubid(), member.getMemberid());
//        if (temp.isPresent()) {
//            return temp.get();
//        }
//
//
//
//
//        newClubMember.getMember().setMemberid(clubMember.getMember().getMemberid());
//        newClubMember.getMember().setMemberid(member.getMemberid());
//        newClubMember.getClub().setClubid(club.getClubid());
//
//        newClubMember.getMember().getReactions().clear();
//        for (MemberReactions mr : member.getReactions()) {
//            Reaction addReaction = reactionService.findReactionById(mr.getReaction().getReactionid());
//            newClubMember.getMember().getReactions().add(new MemberReactions(newClubMember.getMember(), addReaction, mr.getClubactivity()));
//        }
//
//        return clubMembersRepo.save(newClubMember);
        return clubMembersRepo.save(clubMember);
    }
}
