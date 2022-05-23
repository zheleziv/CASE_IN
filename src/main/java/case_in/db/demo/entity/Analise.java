package case_in.db.demo.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "analise", schema = "catalog")
public class Analise
{
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name="id")
    @GeneratedValue
    private Long id;

    @Column(name="transport_id")
    private String transportId;

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int date) {
        this.month = date;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public long getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(long drivingTime) {
        this.drivingTime = drivingTime;
    }

    public long getEngineOperatingTime() {
        return engineOperatingTime;
    }

    public void setEngineOperatingTime(long engineOperatingTime) {
        this.engineOperatingTime = engineOperatingTime;
    }

    public long getEngineInMotionTime() {
        return engineInMotionTime;
    }

    public void setEngineInMotionTime(long engineInMotionTime) {
        this.engineInMotionTime = engineInMotionTime;
    }

    public long getEngineWOMotionTime() {
        return engineWOMotionTime;
    }

    public void setEngineWOMotionTime(long engineWOMotionTime) {
        this.engineWOMotionTime = engineWOMotionTime;
    }

    public long getEngineIdlingTime() {
        return engineIdlingTime;
    }

    public void setEngineIdlingTime(long engineIdlingTime) {
        this.engineIdlingTime = engineIdlingTime;
    }

    public long getEngineNormaRpmTime() {
        return engineNormaRpmTime;
    }

    public void setEngineNormaRpmTime(long engineNormaRpmTime) {
        this.engineNormaRpmTime = engineNormaRpmTime;
    }

    public long getEngineMaxRpmTime() {
        return engineMaxRpmTime;
    }

    public void setEngineMaxRpmTime(long engineMaxRpmTime) {
        this.engineMaxRpmTime = engineMaxRpmTime;
    }

    public long getEngineOffTime() {
        return engineOffTime;
    }

    public void setEngineOffTime(long engineOffTime) {
        this.engineOffTime = engineOffTime;
    }

    public long getEngineUnderLoadTime() {
        return engineUnderLoadTime;
    }

    public void setEngineUnderLoadTime(long engineUnderLoadTime) {
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

    public Analise() {
    }


    @Column(name="month")
    private int month;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Column(name="days")
    private int days;
    @Column(name="sum_mileage")
    private double mileage;
    @Column(name="sum_driving_time")
    private long drivingTime;
    @Column(name="sum_engine_operating_time")
    private long engineOperatingTime;
    @Column(name="sum_engine_in_motion_time")
    private long engineInMotionTime;
    @Column(name="sum_engine_WO_motion_time")
    private long engineWOMotionTime;
    @Column(name="sum_engine_idling_time")
    private long engineIdlingTime;
    @Column(name="sum_engine_normal_rpm_time")
    private long engineNormaRpmTime;
    @Column(name="sum_engine_max_rpm_time")
    private long engineMaxRpmTime;
    @Column(name="sum_engine_off_time")
    private long engineOffTime;
    @Column(name="sum_engine_under_load_time")
    private long engineUnderLoadTime;
    @Column(name="sum_initial_fuel_volume")
    private double initialFuelVolume;
    @Column(name="sum_final_fuel_volume")
    private double finalFuelVolume;

    public double getDifEngineOffTime() {
        return difEngineOffTime;
    }

    public void setDifEngineOffTime(double difEngineOffTime) {
        this.difEngineOffTime = difEngineOffTime;
    }
    //nastoyashii analiz
    @Column(name="dif_engine_off_time")
    private double difEngineOffTime;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    @Column(name="season")
    private int season;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name="type")
    private String type;
}
