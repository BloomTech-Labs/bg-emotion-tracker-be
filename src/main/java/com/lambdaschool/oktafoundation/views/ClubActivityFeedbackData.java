package com.lambdaschool.oktafoundation.views;

import java.util.Date;

public interface ClubActivityFeedbackData {
    Date getCreateddate();
    long getActivityid();
    long getClubid();
    long getMember();
    long getReactionid();
}
