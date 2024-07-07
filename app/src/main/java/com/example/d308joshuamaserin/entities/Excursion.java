package com.example.d308joshuamaserin.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionTitle;
    private int vacationID;

    public Excursion(int excursionID, String excursionTitle, int vacationID) {
        this.excursionID = excursionID;
        this.excursionTitle = excursionTitle;
        this.vacationID = vacationID;
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
}
