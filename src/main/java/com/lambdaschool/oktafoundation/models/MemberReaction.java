package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

//id
// member id
// reaction id
// created id

@Entity
@Table(name = "memberreactions")
public class MemberReaction extends Auditable
{
    @Id
    @GeneratedValue
    private long memberreactionid;

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

    @ManyToOne
    @JoinColumn(name="clubactivityid")
    @JsonIgnoreProperties(value="reactions")
    private ClubActivity clubactivity;

//    TODO why do we not have a constructor, getter, and setters?

    public MemberReaction() {
    }

    public MemberReaction(Member member, Reaction reaction, Boolean checkedin, ClubActivity clubactivity) {
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

    public ClubActivity getClubactivity() {
        return clubactivity;
    }

    public void setClubactivity(ClubActivity clubactivity) {
        this.clubactivity = clubactivity;
    }
}
