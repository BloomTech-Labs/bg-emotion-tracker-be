package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.views.AlertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "memberReactionService")
public class MemberReactionServiceImpl implements MemberReactionService{
    @Autowired
    private MemberReactionRepository memberReactionRepository;
    @Override
    public List<MemberReactions> findAll() {
        List<MemberReactions> memberReactionsList = new ArrayList<>();

        memberReactionRepository.findAll()
                .iterator()
                .forEachRemaining(memberReactionsList::add);
        return memberReactionsList;

    }

    @Transactional
    @Override
    public MemberReactions save(MemberReactions memberreactions) {
        MemberReactions newMr = new MemberReactions();

        if (memberreactions.getMemberreactionid() != 0)
        {
            newMr = memberReactionRepository.findById(memberreactions.getMemberreactionid())
                    .orElseThrow(() -> new ResourceNotFoundException("Member Reaction id " + memberreactions.getMemberreactionid() + "not found!"));
        }

        newMr.setMember((memberreactions.getMember()));
        newMr.setReaction((memberreactions.getReaction()));
        newMr.setClubactivity((memberreactions.getClubactivity()));
        newMr.setReactionresolved((memberreactions.isReactionresolved()));
        return memberReactionRepository.save(newMr);
    }
//hey
    @Transactional
    @Override
    public MemberReactions update(long id, MemberReactions memberreactions) {

        MemberReactions currentMr = memberReactionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("MemberReaction id " + id + " not found!"));

        currentMr.setReactionresolved(!currentMr.isReactionresolved());
        return memberReactionRepository.save(currentMr);
    }


    @Override
    public MemberReactions findMemberReactionById(Long id) throws ResourceNotFoundException
    {
        return memberReactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member Reaction id" + id + "not found."));
    }

    @Override
    public List<AlertData> getMemberReactionsByReactionresolved() {
        return memberReactionRepository.getMemberReactionsByReactionresolved();
    }
}
