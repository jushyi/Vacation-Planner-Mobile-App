package com.example.d308joshuamaserin.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionTitle;
    private int vacationID;
    private String excursionDate;

    public Excursion(int excursionID,
                     String excursionTitle,
                     int vacationID,
                     String excursionDate) {
        this.excursionID = excursionID;
        this.excursionTitle = excursionTitle;
        this.vacationID = vacationID;
        this.excursionDate = excursionDate;
    }

    //getters
    public int getExcursionID() {
        return excursionID;
    }

    public String getExcursionTitle() {
        return excursionTitle;
    }

    public int getVacationID() {
        return vacationID;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    //setters
    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }
}
