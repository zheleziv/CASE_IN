package case_in.db.demo.response;

import case_in.db.demo.entity.Analise;

import javax.persistence.Column;

public class AnaliseMonthResponse {

    private String transportId;
    private int month;
    private double mileage;
    private long drivingTime;
    private long engineOperatingTime;
    private long engineInMotionTime;
    private long engineWOMotionTime;
    private long engineIdlingTime;
    private long engineNormaRpmTime;
    private long engineMaxRpmTime;
    private long engineOffTime;
    private long engineUnderLoadTime;

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    private double initialFuelVolume;
    private double finalFuelVolume;
    private int days;

    public AnaliseMonthResponse(String transportId, int month, double mileage, long drivingTime,
                                long engineOperatingTime, long engineInMotionTime, long engineWOMotionTime,
                                long engineIdlingTime, long engineNormaRpmTime, long engineMaxRpmTime,
                                long engineOffTime, long engineUnderLoadTime, double initialFuelVolume,
                                double finalFuelVolume, int days ) {
        this.transportId = transportId;
        this.month = month;
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
        this.days = days;
    }

    public AnaliseMonthResponse(Analise analise) {
        this.transportId = analise.getTransportId();
        this.month = analise.getMonth();
        this.mileage = analise.getMileage();
        this.drivingTime = analise.getDrivingTime();
        this.engineOperatingTime = analise.getEngineOperatingTime();
        this.engineInMotionTime = analise.getEngineInMotionTime();
        this.engineWOMotionTime = analise.getEngineWOMotionTime();
        this.engineIdlingTime = analise.getEngineIdlingTime();
        this.engineNormaRpmTime = analise.getEngineNormaRpmTime();
        this.engineMaxRpmTime = analise.getEngineMaxRpmTime();
        this.engineOffTime = analise.getEngineOffTime();
        this.engineUnderLoadTime = analise.getEngineUnderLoadTime();
        this.initialFuelVolume = analise.getInitialFuelVolume();
        this.finalFuelVolume = analise.getFinalFuelVolume();
        this.days = analise.getDays();
    }

}
