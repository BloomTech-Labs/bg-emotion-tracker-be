package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @OneToOne
    @JoinColumn(name = "reactionid")
    private MemberReaction memberReaction;

    @Column(nullable = false, unique = true)
    private String reactionvalue;

    public MemberReaction getMemberReaction() {
        return memberReaction;
    }

    public void setMemberReaction(MemberReaction memberReaction) {
        this.memberReaction = memberReaction;
    }

    public String getReactionvalue() {
        return reactionvalue;
    }

    public void setReactionvalue(String reactionvalue) {
        this.reactionvalue = reactionvalue;
    }
}
