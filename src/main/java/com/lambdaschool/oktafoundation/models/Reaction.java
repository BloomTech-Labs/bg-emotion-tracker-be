package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * The entity allowing interaction with the reactions table
 */
@Entity
@Table(name = "reactions")
public class Reaction extends Auditable {
    /**
     * The primary key (long) of the reaction table.
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
     * Part of the join relationship between Member and Reactions
     * connects reaction to the memberreaction combination
     */

    /**
     * Default constructor used primarily by the JPA.
     */
    public Reaction() {
    }
    /**
     * Getter for reactionid
     *
     * @return the reactionid (long) of the Reaction
     */
    public long getReactionid() {
        return reactionid;
    }
    /**
     * Setter for reactionid. Used primary for seeding data
     *
     * @param reactionid the new reactionid (long) of the reaction
     */
    public void setReactionid(long reactionid) {
        this.reactionid = reactionid;
    }

    /**
     * Getter for reactionvalue
     *
     * @return the reactionvalue (String)
     */
    public String getReactionvalue() {
        return reactionvalue;
    }
    /**
     * setter for reactionvalue
     *
     * @param reactionvalue the new reactionvalue (String)
     */
    public void setReactionvalue(String reactionvalue) {
        this.reactionvalue = reactionvalue;
    }
}
