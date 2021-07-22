package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Class to represent the complex primary key for ClubMembers
 */
@Embeddable
public class ClubMembersId
    implements Serializable
{
    /**
     * The id of the Club
     */
    private long club;

    /**
     * The id of the Member
     */
    private long member;

    /**
     * The default constructor required by JPA
     */
    public ClubMembersId()
    {
    }

    /**
     * Getter for the member id
     *
     * @return The member id (long)
     */
    public long getMember()
    {
        return member;
    }

    /**
     * Setter for the member id
     *
     * @param member The new member id for this object
     */
    public void setMember(long member)
    {
        this.member = member;
    }

    /**
     * Getter for the club id
     *
     * @return The club id (long)
     */
    public long getClub()
    {
        return club;
    }

    /**
     * Setter for the club id
     *
     * @param club The new club id for this object
     */
    public void setClub(long club)
    {
        this.club = club;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ClubMembersId that = (ClubMembersId) o;
        return member == that.member &&
            club == that.club;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
