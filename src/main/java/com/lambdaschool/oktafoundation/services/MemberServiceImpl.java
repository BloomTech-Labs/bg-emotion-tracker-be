package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private ClubService clubService;

    @Override
    public List<Member> findAll() {
        List<Member> list = new ArrayList<>();
        memberrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        list.sort((m1,m2)-> (int)(m1.getMember_table_id()- m2.getMember_table_id()));
        return list;
    }

    @Override
    public Member findMemberById(long id) throws ResourceNotFoundException {
        return memberrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member id " + id + " not found!"));
    }

    @Override
    public Member findMemberByMemberId(String memberid) throws ResourceNotFoundException {
        return memberrepos.findMemberByMemberid(memberid).orElseThrow(() -> new ResourceNotFoundException("Member with memberid" + memberid + " not found"));
    }

    @Override
    public void deleteAll(){
        memberrepos.deleteAll();
    }


    @Override
    public Member save(Member member) {

        return memberrepos.save(member);
    }

    //Toggles active and deactivated states of members
    @Transactional
    @Override
    public Member update(long memberid)
    {
        Member member = memberrepos.findById(memberid)
            .orElseThrow(()-> new ResourceNotFoundException("Member " + memberid + " not found!"));

        member.setActive(!member.isActive());
        return memberrepos.save(member);
    }
}
