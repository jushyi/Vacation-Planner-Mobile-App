package com.example.d308joshuamaserin.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    public int vacationId;
    private String vacationTitle;

    public Vacation(int vacationId, String vacationTitle) {
        this.vacationId = vacationId;
        this.vacationTitle = vacationTitle;
    }

    public String toString() {
        return vacationTitle;
    }

    //getters
    public int getVacationId() {
        return vacationId;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    //setters
    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }
}