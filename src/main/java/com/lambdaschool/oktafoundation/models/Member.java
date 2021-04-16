package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="members")
public class Member extends Auditable
{
    @Id
    @Column(unique = true)
    public long member_table_id;

    @Column(unique = true, nullable = false)
    private String memberid;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value="member", allowSetters = true)
    public Set<MemberReaction> reactions = new HashSet<>();

    public Member() {
    }

    public Member(String memberid) {
        this.memberid = memberid;
    }

    public long getMember_table_id() {
        return member_table_id;
    }

    public void setMember_table_id(long member_table_id) {
        this.member_table_id = member_table_id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Set<MemberReaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<MemberReaction> reactions) {
        this.reactions = reactions;
    }
}
