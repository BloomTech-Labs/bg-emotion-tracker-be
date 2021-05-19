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
     * The id of the club
     */
    private long club;


    /**
     * The id of the member
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
     * @return long the member id
     */
    public long getMember()
    {
        return member;
    }

    /**
     * Setter for the member id
     *
     * @param member the new member id for this object
     */
    public void setMember(long member)
    {
        this.member = member;
    }

    /**
     * Getter for the club id
     *
     * @return long the club id
     */
    public long getClub()
    {
        return club;
    }

    /**
     * The setter for the club id
     *
     * @param club the new club id for this object
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
        // boolean temp = (o.getClass() instanceof Class);
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
