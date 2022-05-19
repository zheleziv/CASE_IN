package case_in.db.demo.response;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

public class DatasetResponse
{
    private String transportId;
    private Date date;
    private double mileage;
    private Time drivingTime;
    private Time engineOperatingTime;
    private Time engineInMotionTime;
    private Time engineWOMotionTime;
    private Time engineIdlingTime;
    private Time engineNormaRpmTime;
    private Time engineMaxRpmTime;
    private Time engineOffTime;
    private Time engineUnderLoadTime;
    private double initialFuelVolume;
    private double finalFuelVolume;

    public DatasetResponse(String transportId, Date date,
                           double mileage, Time drivingTime,
                           Time engineOperatingTime, Time engineInMotionTime,
                           Time engineWOMotionTime, Time engineIdlingTime,
                           Time engineNormaRpmTime, Time engineMaxRpmTime,
                           Time engineOffTime, Time engineUnderLoadTime,
                           double initialFuelVolume, double finalFuelVolume) {
        this.transportId = transportId;
        this.date = date;
        this.mileage = mileage;
        this.drivingTime = drivingTime;
        this.engineOperatingTime = engineOperatingTime;
        this.engineInMotionTime = engineInMotionTime;
        this.engineWOMotionTime = engineWOMotionTime;
        this.engineIdlingTime = engineIdlingTime;
        this.engineNormaRpmTime = engineNormaRpmTime;
        this.engineMaxRpmTime = engineMaxRpmTime;
        this.engineOffTime = engineOffTime;
        this.engineUnderLoadTime = engineUnderLoadTime;
        this.initialFuelVolume = initialFuelVolume;
        this.finalFuelVolume = finalFuelVolume;
    }

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public Time getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(Time drivingTime) {
        this.drivingTime = drivingTime;
    }

    public Time getEngineOperatingTime() {
        return engineOperatingTime;
    }

    public void setEngineOperatingTime(Time engineOperatingTime) {
        this.engineOperatingTime = engineOperatingTime;
    }

    public Time getEngineInMotionTime() {
        return engineInMotionTime;
    }

    public void setEngineInMotionTime(Time engineInMotionTime) {
        this.engineInMotionTime = engineInMotionTime;
    }

    public Time getEngineWOMotionTime() {
        return engineWOMotionTime;
    }

    public void setEngineWOMotionTime(Time engineWOMotionTime) {
        this.engineWOMotionTime = engineWOMotionTime;
    }

    public Time getEngineIdlingTime() {
        return engineIdlingTime;
    }

    public void setEngineIdlingTime(Time engineIdlingTime) {
        this.engineIdlingTime = engineIdlingTime;
    }

    public Time getEngineNormaRpmTime() {
        return engineNormaRpmTime;
    }

    public void setEngineNormaRpmTime(Time engineNormaRpmTime) {
        this.engineNormaRpmTime = engineNormaRpmTime;
    }

    public Time getEngineMaxRpmTime() {
        return engineMaxRpmTime;
    }

    public void setEngineMaxRpmTime(Time engineMaxRpmTime) {
        this.engineMaxRpmTime = engineMaxRpmTime;
    }

    public Time getEngineOffTime() {
        return engineOffTime;
    }

    public void setEngineOffTime(Time engineOffTime) {
        this.engineOffTime = engineOffTime;
    }

    public Time getEngineUnderLoadTime() {
        return engineUnderLoadTime;
    }

    public void setEngineUnderLoadTime(Time engineUnderLoadTime) {
        this.engineUnderLoadTime = engineUnderLoadTime;
    }

    public double getInitialFuelVolume() {
        return initialFuelVolume;
    }

    public void setInitialFuelVolume(double initialFuelVolume) {
        this.initialFuelVolume = initialFuelVolume;
    }

    public double getFinalFuelVolume() {
        return finalFuelVolume;
    }

    public void setFinalFuelVolume(double finalFuelVolume) {
        this.finalFuelVolume = finalFuelVolume;
    }
}
