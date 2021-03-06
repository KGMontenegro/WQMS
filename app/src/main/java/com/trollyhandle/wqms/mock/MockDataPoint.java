package com.trollyhandle.wqms.mock;

public class MockDataPoint {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private String id;
    private double temp;
    private String humidity;
    private String co2;
    private String particle;
    private String ozone;
    private String time;

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

//    public DateTime getDate() {
//        return DateTimeFormat.forPattern(DATE_PATTERN).parseDateTime(time);
//    }

    @Override
    public String toString() {
        return "DataPoint(id:" + id + ", temp:" + temp + ", co2:" + co2 + ", ozone:" + ozone
                + ", humidity:" + humidity + ", particle:" + particle + ", time:" + time + ")\n";
    }
}
