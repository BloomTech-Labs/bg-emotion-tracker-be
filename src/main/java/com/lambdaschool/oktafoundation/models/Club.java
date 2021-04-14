package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;

@Entity
@Table(name = "clubs")
public class Club
    extends Auditable
{
    // Getting Error about "'One To Many' attribute type should be a container"
    @Id
    @OneToMany
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long clubid;

    @Column(nullable = false, unique = true)
    private String clubname;

    public Long getClubid() {
        return clubid;
    }

    public void setClubid(Long id) {
        this.clubid = id;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }
}
