package com.nederlonder.wqms.models;

public class MockDataPoint {

    String id;
    double temp;
    String humidity;
    String co2;
    String particle;
    String ozone;
    String time;

    public String getId() {
        return id;
    }

    public double getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getCo2() {
        return co2;
    }

    public String getParticle() {
        return particle;
    }

    public String getOzone() {
        return ozone;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "DataPoint(id:" + id + ", temp:" + temp + ", co2:" + co2 + ", ozone:" + ozone
                + ", humidity:" + humidity + ", particle:" + particle + ", time:" + time + ")\n";
    }
}
