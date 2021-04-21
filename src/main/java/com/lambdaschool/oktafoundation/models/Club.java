package com.lambdaschool.oktafoundation.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ApiModel
@Entity(name = "Club")
@Table(name = "clubs")
@NaturalIdCache
public class Club
    extends Auditable
{
    @ApiModelProperty(
            name = "Club Id",
            value = "primary key for club",
            required = true,
            example = "1"
    )
    // Id for table itself
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private long clubid;

    @OneToMany(mappedBy = "club",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnoreProperties(value = "club", allowSetters = true)
    private Set<ClubActivities> activities = new HashSet<>();

    @OneToMany(mappedBy = "club",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "club", allowSetters = true)
    private Set<ClubUsers> users = new HashSet<>();


    @ApiModelProperty(
            name = "Club Name",
            value = "Name of the Club",
            required = true,
            example = "South Palmdale Club"
    )
    @NaturalId
    @Column(nullable = false, unique = true)
    private String clubname;

    public Club() {
    }

    public Club(String clubname) {
        this.clubname = clubname;
    }

    public long getClubid() {
        return clubid;
    }

    public void setClubid(long clubid) {
        this.clubid = clubid;
    }

    public Set<ClubActivities> getActivities() {
        return activities;
    }

    public void setActivities(Set<ClubActivities> activities) {
        this.activities = activities;
    }

    public Set<ClubUsers> getUsers() {
        return users;
    }

    public void setUsers(Set<ClubUsers> users) {
        this.users = users;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

}
