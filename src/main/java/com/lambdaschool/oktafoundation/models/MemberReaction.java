package com.lambdaschool.oktafoundation.models;

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
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long memberreactionid;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberid;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reactionid;

    @Column(nullable = false)
    private Boolean checkedin;


}
