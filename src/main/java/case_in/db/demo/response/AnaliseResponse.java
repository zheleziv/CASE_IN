package case_in.db.demo.response;

public class AnaliseResponse {
    long sumOperatTime;
    long sumMotTime;
    long sumUnderTime;
    double sumStartFuel;
    double sumFinalFuel;
    double suMil;

    public int getDiffday() {
        return diffday;
    }

    public void setDiffday(int diffday) {
        this.diffday = diffday;
    }

    int diffday;

    public AnaliseResponse(long sumOperatTime, long sumMotTime, long sumUnderTime, double sumStartFuel, double sumFinalFuel, double suMil, int diffday) {
        this.sumOperatTime = sumOperatTime;
        this.sumMotTime = sumMotTime;
        this.sumUnderTime = sumUnderTime;
        this.sumStartFuel = sumStartFuel;
        this.sumFinalFuel = sumFinalFuel;
        this.suMil = suMil;
        this.diffday = diffday;
    }

    public long getSumOperatTime() {
        return sumOperatTime;
    }

    public void setSumOperatTime(long sumOperatTime) {
        this.sumOperatTime = sumOperatTime;
    }

    public long getSumMotTime() {
        return sumMotTime;
    }

    public void setSumMotTime(long sumMotTime) {
        this.sumMotTime = sumMotTime;
    }

    public long getSumUnderTime() {
        return sumUnderTime;
    }

    public void setSumUnderTime(long sumUnderTime) {
        this.sumUnderTime = sumUnderTime;
    }

    public double getSumStartFuel() {
        return sumStartFuel;
    }

    public void setSumStartFuel(double sumStartFuel) {
        this.sumStartFuel = sumStartFuel;
    }

    public double getSumFinalFuel() {
        return sumFinalFuel;
    }

    public void setSumFinalFuel(double sumFinalFuel) {
        this.sumFinalFuel = sumFinalFuel;
    }

    public double getSuMil() {
        return suMil;
    }

    public void setSuMil(double suMil) {
        this.suMil = suMil;
    }
}
