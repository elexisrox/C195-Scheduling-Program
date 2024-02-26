package app.model;

import java.time.LocalDateTime;

/**
 * Appointments class provides appointment objects.
 */

public class Appointments {

    int apptID;
    String apptTitle;
    String apptDesc;
    String apptLocation;
    int apptContactID;
    String apptType;
    // TODO: Not sure if correct data types below.
    LocalDateTime apptStartDate;
    LocalDateTime apptStartTime;
    LocalDateTime apptEndDate;
    LocalDateTime apptEndTime;

}
