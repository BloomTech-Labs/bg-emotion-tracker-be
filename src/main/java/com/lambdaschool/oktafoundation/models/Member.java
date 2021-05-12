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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long member_table_id;

    /**
     * The primary key (long) of the members table.
     */
    @Column(unique = true, nullable = false)
    private String memberid;
    /**
     * Part of the join relationship between Member and Reactions
     * connects member to the member reaction combination
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value="member", allowSetters = true)
    public Set<MemberReactions> reactions = new HashSet<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public Member() {
    }
    /**
     * Given the params, create a new member object
     * <p>
     * @param memberid The memberid (String) of the member
     * memberid is assigned to member by a csv list
     */
    public Member(String memberid) {
        this.memberid = memberid;
    }
    /**
     * Getter for member_table_id
     *
     * @return the member_table_id (long) of the Member
     */
    public long getMember_table_id() {
        return member_table_id;
    }
    /**
     * Setter for member_table_id. Used primary for seeding data
     *
     * @param member_table_id the new member_table_id (long) of the member
     */
    public void setMember_table_id(long member_table_id) {
        this.member_table_id = member_table_id;
    }
    /**
     * Getter for memberid
     *
     * @return the memberid (String) of the Member
     */
    public String getMemberid() {
        return memberid;
    }
    /**
     * Setter for memberid. Used primary for seeding data
     *
     * @param memberid the new memberid (String) of the member
     */
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }
    /**
     * Getter for member reaction combinations
     *
     * @return A list of member reaction combinations associated with this member
     */
    public Set<MemberReactions> getReactions() {
        return reactions;
    }
    /**
     * Setter for member reaction combinations
     *
     * @param reactions Change the list of member reaction combinations associated with this member to this one
     */
    public void setReactions(Set<MemberReactions> reactions) {
        this.reactions = reactions;
    }
}
