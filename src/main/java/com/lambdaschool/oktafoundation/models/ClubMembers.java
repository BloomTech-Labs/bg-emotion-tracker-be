package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The entity allowing interaction with the clubmembers table.
 * The join table between clubs and members.
 * <p>
 * Table enforces a unique constraint of the combination of clubid and memberid.
 * These two together form the primary key.
 * <p>
 * When you have a compound primary key, you must implement Serializable for Hibernate
 * When you implement Serializable you must implement equals and hash code
 */
@Entity
@Table(name = "clubmembers")
@IdClass(ClubMembersId.class)
public class ClubMembers
    extends Auditable
    implements Serializable
{
    /**
     * 1/2 of the primary key (long) for clubmembers.
     * Also is a foreign key into the clubs table
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = {"members","activities","users"},
        allowSetters = true)
    private Club club;

    /**
     * 1/2 of the primary key (long) for clubmembers.
     * Also is a foreign key into the members table
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "member_table_id")
    @JsonIgnoreProperties(value = {"clubs","reactions"},
        allowSetters = true)
    private Member member;

    /**
     * Default constructor used primarily by the JPA.
     */
    public ClubMembers()
    {
    }

    /**
     * Given the params, create a new club member combination object
     *
     * @param c The Club object of this relationship
     * @param m The Member object of this relationship
     */
    public ClubMembers(Club c, Member m)
    {
        this.club = c;
        this.member = m;
    }

    /**
     * The getter for Club
     *
     * @return The complete Club object associated with this club member combination
     */
    public Club getClub()
    {
        return club;
    }

    /**
     * Setter for Club
     *
     * @param club Change the Club object associated with this club member combination to this one
     */
    public void setClub(Club club)
    {
        this.club = club;
    }

    /**
     * Getter for Member
     *
     * @return The complete Member object associated with this club member combination
     */
    public Member getMember()
    {
        return member;
    }

    /**
     * Setter for Member
     *
     * @param member Change the Member object associated with this club member combination to this one
     */
    public void setMember(Member member)
    {
        this.member = member;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ClubMembers))
        {
            return false;
        }
        ClubMembers that = (ClubMembers) o;
        return ((club == null) ? 0 : club.getClubid()) == ((that.club == null) ? 0 : that.club.getClubid()) &&
            ((member == null) ? 0 : member.getMember_table_id()) == ((that.member == null) ? 0 : that.member.getMember_table_id());
    }

    @Override
    public int hashCode()
    {

        return 37;
    }
}
