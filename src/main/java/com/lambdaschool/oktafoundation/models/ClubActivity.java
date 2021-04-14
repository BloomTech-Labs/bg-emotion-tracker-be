package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;

@Entity
@Table(name = "clubactivites")
public class ClubActivity extends Auditable
{
    // What do we need an ID here for?
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long clubactivityid;

    // needs to bring in club id from club

    //needs to bring in activity id from activity
}
