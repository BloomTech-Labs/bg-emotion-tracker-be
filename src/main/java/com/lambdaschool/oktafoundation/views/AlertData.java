package com.lambdaschool.oktafoundation.views;

import java.util.Date;

public interface AlertData {
    long getId();
    long getMember();
    long getActivityId();
    String getActivities();
    String getClubname();
    long getReactionid();
    String getReactionvalue();
    long getReactionint();
    long getClubid();
    Date getCreateddate();
}
