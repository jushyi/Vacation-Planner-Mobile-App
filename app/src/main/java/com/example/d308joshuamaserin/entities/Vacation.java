package com.example.d308joshuamaserin.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    public int vacationId;
    private String vacationTitle;
    private String vacationHotel;
    private String startDate;
    private String endDate;

    public Vacation(int vacationId,
                    String vacationTitle,
                    String vacationHotel,
                    String startDate,
                    String endDate) {
        this.vacationId = vacationId;
        this.vacationTitle = vacationTitle;
        this.vacationHotel = vacationHotel;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getVacationHotel() {
        return vacationHotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    //setters
    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    public void setVacationHotel(String vacationHotel) {
        this.vacationHotel = vacationHotel;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}