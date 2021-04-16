package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reactions")
public class Reaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reacitonid;

    @Column(nullable = false, unique = true)
    private String reactionvalue;

    @OneToMany(mappedBy = "reactions")
    @JsonIgnoreProperties(value="reactions", allowSetters = true)
    public Set<MemberReaction> member = new HashSet<>();

    public Reaction() {
    }

    public long getReacitonid() {
        return reacitonid;
    }

    public void setReacitonid(long reacitonid) {
        this.reacitonid = reacitonid;
    }

    public Set<MemberReaction> getMember() {
        return member;
    }

    public void setMember(Set<MemberReaction> member) {
        this.member = member;
    }

    public String getReactionvalue() {
        return reactionvalue;
    }

    public void setReactionvalue(String reactionvalue) {
        this.reactionvalue = reactionvalue;
    }
}
