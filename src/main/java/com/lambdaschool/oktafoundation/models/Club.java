package com.lambdaschool.oktafoundation.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clubs")
public class Club
    extends Auditable
{
    // Id for table itself
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private long clubid;

    @OneToMany(mappedBy = "club",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnoreProperties(value = "club", allowSetters = true)
    private Set<ClubActivity> activities = new HashSet<>();

    @Column(nullable = false, unique = true)
    private String clubname;

    public long getClubid() {
        return clubid;
    }

    public void setClubid(long clubid) {
        this.clubid = clubid;
    }

    public Set<ClubActivity> getActivities() {
        return activities;
    }

    public void setActivities(Set<ClubActivity> activities) {
        this.activities = activities;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }
}
