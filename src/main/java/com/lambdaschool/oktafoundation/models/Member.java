package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;

@Entity
@Table(name="members")
public class Member
{
    @Id
    @OneToOne
    @JoinColumn(name = "memberid")
    private MemberReaction memberReaction;

    public MemberReaction getMemberReaction() {
        return memberReaction;
    }

    public void setMemberReaction(MemberReaction memberReaction) {
        this.memberReaction = memberReaction;
    }
}
