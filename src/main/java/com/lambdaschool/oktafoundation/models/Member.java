package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="members")
public class Member
{
    @Id
    @Column(unique = true)
    public long member_table_id;

    @Column(unique = true, nullable = false)
    private String memberid;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value="member", allowSetters = true)
    public Set<MemberReaction> reactions = new HashSet<>();


}
