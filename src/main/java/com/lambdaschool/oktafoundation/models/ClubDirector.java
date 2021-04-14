package com.lambdaschool.oktafoundation.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "clubdirectors")
public class ClubDirector extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clubdirectorid;

    @Id
    @OneToOne
    @JoinColumn(name = "clubid")
    private Club club;

    public long getClubdirectorid() {
        return clubdirectorid;
    }

    public void setClubdirectorid(long clubdirectorid) {
        this.clubdirectorid = clubdirectorid;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
