package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reactionid;

    @Column(nullable = false, unique = true)
    private String reactionvalue;

//    @OneToMany(mappedBy = "reactions")
//    @JsonIgnoreProperties(value="reactions", allowSetters = true)
//    public Set<MemberReaction> member = new HashSet<>();

    public Reaction() {
    }

    public long getReactionid() {
        return reactionid;
    }

    public void setReactionid(long reacitonid) {
        this.reactionid = reacitonid;
    }

//    public Set<MemberReaction> getMember() {
//        return member;
//    }
//
//    public void setMember(Set<MemberReaction> member) {
//        this.member = member;
//    }

    public String getReactionvalue() {
        return reactionvalue;
    }

    public void setReactionvalue(String reactionvalue) {
        this.reactionvalue = reactionvalue;
    }
}
