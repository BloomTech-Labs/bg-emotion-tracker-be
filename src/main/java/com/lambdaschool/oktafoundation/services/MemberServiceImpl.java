package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.models.Reaction;
import com.lambdaschool.oktafoundation.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "memberService")
public class MemberServiceImpl implements MemberService {
    /**
     * Connects this service to the Member table.
     */
    @Autowired
    private MemberRepository memberrepos;

    @Autowired
    private ReactionService reactionService;

    @Override
    public List<Member> findAll() {
        List<Member> list = new ArrayList<>();
        memberrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Member findMemberById(long id) throws ResourceNotFoundException {
        return memberrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member id " + id + " not found!"));
    }

    @Override
    public Member save(Member member) {
        Member newMember = new Member();

        if (member.getMember_table_id() != 0) {
            memberrepos.findById(member.getMember_table_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Member id " + member.getMember_table_id() + " not found!!"));
            newMember.setMember_table_id(member.getMember_table_id());
        }

        newMember.setMemberid(member.getMemberid());

        newMember.getReactions().clear();
        for (MemberReactions mr : member.getReactions()) {
            Reaction addReaction = reactionService.findReactionById(mr.getReaction().getReactionid());
            newMember.getReactions().add(new MemberReactions(newMember, addReaction, mr.getCheckedin(), mr.getClubactivity()));
        }

        return memberrepos.save(newMember);
    }
}
