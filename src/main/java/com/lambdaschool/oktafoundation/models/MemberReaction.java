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
    @JoinColumn(name="activity_id")
    @JsonIgnoreProperties(value="reactions")
    private Activity activity_id;

}
