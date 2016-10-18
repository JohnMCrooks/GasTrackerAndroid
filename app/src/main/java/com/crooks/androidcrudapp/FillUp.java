package com.crooks.androidcrudapp;

import java.util.Date;

/**
 * Created by johncrooks on 10/12/16.
 */

public class FillUp {
    int id;
    int softId;
    int newOdometer;
    String date;
    double costPerGallon;
    double gallonsPumped;
    double totalCost;
    double milesPerGallon;



    public FillUp() {
    }

    public FillUp(String date, double costPerGallon, double gallonsPumped, double totalCost) {
        this.date = date;
        this.costPerGallon = costPerGallon;
        this.gallonsPumped = gallonsPumped;
        this.totalCost = totalCost;
    }

    public FillUp(String date, double costPerGallon, double gallonsPumped, double totalCost, int newOdometer) {
        this.date = date;
        this.costPerGallon = costPerGallon;
        this.gallonsPumped = gallonsPumped;
        this.totalCost = totalCost;
        this.newOdometer = newOdometer;
    }

    public FillUp( int softId, String date, double costPerGallon, double gallonsPumped, double totalCost, int newOdometer, double milesPerGallon) {
        this.softId = softId;
        this.date = date;
        this.costPerGallon = costPerGallon;
        this.gallonsPumped = gallonsPumped;
        this.totalCost = totalCost;
        this.newOdometer = newOdometer;
        this.milesPerGallon = milesPerGallon;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCostPerGallon() {
        return costPerGallon;
    }

    public void setCostPerGallon(double costPerGallon) {
        this.costPerGallon = costPerGallon;
    }

    public double getGallonsPumped() {
        return gallonsPumped;
    }

    public void setGallonsPumped(double gallonsPumped) {
        this.gallonsPumped = gallonsPumped;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getSoftId() {
        return softId;
    }

    public void setSoftId(int softId) {
        this.softId = softId;
    }

    public int getNewOdometer() {
        return newOdometer;
    }

    public void setNewOdometer(int newOdometer) {
        this.newOdometer = newOdometer;
    }

    public double getMilesPerGallon() {
        return milesPerGallon;
    }

    public void setMilesPerGallon(double milesPerGallon) {
        this.milesPerGallon = milesPerGallon;
    }
}
