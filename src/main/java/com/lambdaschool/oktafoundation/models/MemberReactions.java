package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;

//id
// member id
// reaction id
// created id

@Entity
@Table(name = "memberreactions")
@IdClass(MemberReactionsId.class)
public class MemberReactions extends Auditable implements Serializable
{

    @Id
    @ManyToOne
    @JoinColumn(name="member_table_id")
    @JsonIgnoreProperties(value="reactions")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name="reaction_id")
    @JsonIgnoreProperties(value="member")
    private Reaction reaction;

    @Column(nullable = false)
    private Boolean checkedin;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "activityid"),
            @JoinColumn(name="clubid")})
    @JsonIgnoreProperties(value="reactions")
    private ClubActivities clubactivity;

    public MemberReactions() {
    }

    public MemberReactions(Member member, Reaction reaction, Boolean checkedin, ClubActivities clubactivity) {
        this.member = member;
        this.reaction = reaction;
        this.checkedin = checkedin;
        this.clubactivity = clubactivity;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    public Boolean getCheckedin() {
        return checkedin;
    }

    public void setCheckedin(Boolean checkedin) {
        this.checkedin = checkedin;
    }

    public ClubActivities getClubactivity() {
        return clubactivity;
    }

    public void setClubactivity(ClubActivities clubactivity) {
        this.clubactivity = clubactivity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MemberReactions that = (MemberReactions) o;

        return ((member == null) ? 0 : member.getMemberid()) == ((that.member == null) ? 0 : that.member.getMemberid()) &&
                ((reaction == null) ? 0 : reaction.getReactionid()) == ((that.reaction == null) ? 0 : that.reaction.getReactionid()) &&
                clubactivity.equals(that.getClubactivity());
    }

    @Override
    public int hashCode() {
        return 22;
    }
}
