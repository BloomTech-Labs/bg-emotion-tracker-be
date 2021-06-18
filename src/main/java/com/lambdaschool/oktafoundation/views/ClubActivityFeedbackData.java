package com.lambdaschool.oktafoundation.views;

import java.util.Date;

public interface ClubActivityFeedbackData {
    long getClubid();
    Date getCreateddate();
    long getReactionid();
    long getActivityid();
    long getMember();
}
