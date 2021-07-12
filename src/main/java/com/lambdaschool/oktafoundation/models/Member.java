package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity allowing interaction with the members table
 */
@Entity
@Table(name="members")
public class Member extends Auditable
{
    /**
     * The primary key (long) of the members table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long member_table_id;

    /**
     * The id (String) used on a member's ID card
     */
    @Column(unique = true, nullable = false)
    private String memberid;

    @Column(nullable = false)
    private boolean active = true;
    /**
     * Part of the join relationship between Member and Reactions
     * connects member to the member reaction combination
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value="member", allowSetters = true)
    private Set<MemberReactions> reactions = new HashSet<>();

    /**
     * Part of the join relationship between Club and Members
     * connects member to the club member combination
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value= "member", allowSetters = true)
    private Set<ClubMembers> clubs = new HashSet<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public Member() {
    }

    /**
     * Given the params, create a new Member object
     * <p>
     * memberid is assigned to member by a csv list
     *
     * @param memberid The memberid (String) of the Member
     */
    public Member(String memberid) {
        this.memberid = memberid;
    }

    /**
     * Getter for member_table_id
     *
     * @return The member_table_id (long) of the Member
     */
    public long getMember_table_id() {
        return member_table_id;
    }

    /**
     * Setter for member_table_id. Used primarily for seeding data
     *
     * @param member_table_id The new member_table_id (long) of the Member
     */
    public void setMember_table_id(long member_table_id) {
        this.member_table_id = member_table_id;
    }

    /**
     * Getter for memberid
     *
     * @return The memberid (String) of the Member
     */
    public String getMemberid() {
        return memberid;
    }

    /**
     * Setter for memberid. Used primarily for seeding data
     *
     * @param memberid The new memberid (String) of the Member
     */
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    /**
     * Getter for member reaction combinations
     *
     * @return A list of member reaction combinations associated with this Member
     */
    public Set<MemberReactions> getReactions() {
        return reactions;
    }

    /**
     * Setter for member reaction combinations
     *
     * @param reactions Change the list of member reaction combinations associated with this Member to this one
     */
    public void setReactions(Set<MemberReactions> reactions) {
        this.reactions = reactions;
    }

    /**
     * Getter for club member combinations
     *
     * @return A list of club member combinations associated with this Member
     */
    public Set<ClubMembers> getClubs() {
        return clubs;
    }

    /**
     * Setter for club member combinations
     *
     * @param clubs Change the list of club member combinations associated with this Member to this one
     */
    public void setClubs(Set<ClubMembers> clubs) {
        this.clubs = clubs;
}
}

