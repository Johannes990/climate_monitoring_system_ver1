package ee.note_parnu.climate_monitoring_system.xml.domain;

public class Reading {
    private int sensor_ID;
    private int device_code;
    private double temperature;
    private double humidity;
    private String tempUnit;

    public void setSensor_ID(int sensor_ID) {
        this.sensor_ID = sensor_ID;
    }

    public void setDevice_code(int device_code) {
        this.device_code = device_code;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
    }

    public int getSensor_ID() {
        return sensor_ID;
    }

    public int getDevice_code() {
        return device_code;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getTempUnit() {
        return tempUnit;
    }
}
