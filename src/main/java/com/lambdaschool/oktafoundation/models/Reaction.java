package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "reactions")
public class Reaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reactionid;

    @Column(nullable = false, unique = true)
    private String reactionvalue;

    @OneToMany
    @JoinColumn(name = "id")
    @JsonIgnoreProperties(value="reactions", allowSetters = true)
    public Set<MemberReactions> member = new HashSet<>();

    public Reaction() {
    }

    public long getReactionid() {
        return reactionid;
    }

    public void setReactionid(long reacitonid) {
        this.reactionid = reacitonid;
    }

    public Set<MemberReactions> getMember() {
        return member;
    }

    public void setMember(Set<MemberReactions> member) {
        this.member = member;
    }

    public String getReactionvalue() {
        return reactionvalue;
    }

    public void setReactionvalue(String reactionvalue) {
        this.reactionvalue = reactionvalue;
    }
}
