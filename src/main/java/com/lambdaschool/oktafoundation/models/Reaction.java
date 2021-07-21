package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;

/**
 * The entity allowing interaction with the reactions table
 */
@Entity
@Table(name = "reactions")
public class Reaction extends Auditable {
    /**
     * The primary key (long) of the reactions table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reactionid;

    /**
     * The reactionvalue (String). Cannot be null and must be unique
     */
    @Column(nullable = false, unique = true)
    private String reactionvalue;

    /**
     * The reactionint (Integer). Cannot be null and must be unique
     * Will hold the value for the reaction to be used for analysis.
     */
    @Column(nullable = false, unique = false)
    private Double reactionint;


    /**
     * Default constructor used primarily by the JPA.
     */
    public Reaction() {
    }


    public Reaction(String reactionvalue, Double reactionint) {
        this.reactionvalue = reactionvalue;
        this.reactionint = reactionint;
    }

    /**
     * Getter for reactionid
     *
     * @return The reactionid (long) of the Reaction
     */
    public long getReactionid() {
        return reactionid;
    }

    /**
     * Setter for reactionid. Used primarily for seeding data
     *
     * @param reactionid The new reactionid (long) of the Reaction
     */
    public void setReactionid(long reactionid) {
        this.reactionid = reactionid;
    }

    /**
     * Getter for reactionvalue
     *
     * @return The reactionvalue (String) of the Reaction
     */
    public String getReactionvalue() {
        return reactionvalue;
    }

    /**
     * setter for reactionvalue
     *
     * @param reactionvalue the new reactionvalue (String) for this Reaction
     */
    public void setReactionvalue(String reactionvalue) {
        this.reactionvalue = reactionvalue;
    }

    /**
     * Getter for reactionvalue
     *
     * @return The reactionint (Integer) of the Reaction
     */
    public Double getReactionint() {
        return reactionint;
    }

    /**
     * setter for reactionint
     *
     * @param reactionint the new reactionint (Integer) for this Reaction
     */
    public void setReactionint(Double reactionint) {
        this.reactionint = reactionint;
    }
}
