package ee.note_parnu.climate_monitoring_system.test.xml.domain;

public class Reading {
    private String passKey;
    private String device;
    private String temp;
    private String relHum;
    private String compQuant;
    private String pressure;
    private String alarms;
    private String compType;
    private String tempU;
    private String pressureU;
    private String timer;

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getAlarms() {
        return alarms;
    }

    public void setAlarms(String alarms) {
        this.alarms = alarms;
    }

    public String getCompType() {
        return compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }

    public String getTempU() {
        return tempU;
    }

    public void setTempU(String tempU) {
        this.tempU = tempU;
    }

    public String getPressureU() {
        return pressureU;
    }

    public void setPressureU(String pressureU) {
        this.pressureU = pressureU;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setRelHum(String relHum) {
        this.relHum = relHum;
    }

    public void setCompQuant(String compQuant) {
        this.compQuant = compQuant;
    }

    public String getPassKey() {
        return this.passKey;
    }

    public String getDevice() {
        return this.device;
    }

    public String getTemp() {
        return this.temp;
    }

    public String getRelHum() {
        return this.relHum;
    }

    public String getCompQuant() {
        return this.compQuant;
    }
}
