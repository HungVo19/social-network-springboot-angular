package com.olympus.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeeklyReportData {
    private int newPostsLasWeek;
    private int newFriendLastWeek;
    private int newLikesLastWeek;
    private int newCommentsLastWeek;
}
