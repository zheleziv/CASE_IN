package case_in.db.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "dataset", schema = "catalog")
public class Dataset
{
    @Id
    @Column(name="id")
    private String id;
    @Column(name="date")
    private Date date;
    @Column(name="mileage")
    private double mileage;
    @Column(name="driving_time")
    private Time drivingTime;
    @Column(name="engine_operating_time")
    private Time engineOperatingTime;
    @Column(name="engine_in_motion_time")
    private Time engineInMotionTime;
    @Column(name="engine_WO_motion_time")
    private Time engineWOMotionTime;
    @Column(name="engine_idling_time")
    private Time engineIdlingTime;
    @Column(name="engine_normal_rpm_time")
    private Time engineNormaRpmTime;
    @Column(name="engine_max_rpm_time")
    private Time engineMaxRpmTime;
    @Column(name="engine_off_time")
    private Time engineOffTime;
    @Column(name="engine_under_load_time")
    private Time engineUnderLoadTime;
    @Column(name="initial_fuel_volume")
    private double initialFuelVolume;
    @Column(name="final_fuel_volume")
    private double finalFuelVolume;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
