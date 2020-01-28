package ftstp.drinkchampaign.ui.drink_tracker;

import java.util.Date;

public class Drink {
    private Date drink_time;
    private double STANDARD_DRINK_GRAMS = 14.0;
    private int MILLISECONDS_TO_HOURS = 360000;
    private int COMPLETE_HOUR = 2;
    private double ALCOHOL_DIFFUSED = -1.0;

    public Drink(){
        drink_time = new Date();
    }

    public String getTimeString(){
        return Long.toString(drink_time.getTime());
    }

    public void setTime(String time_string){
        drink_time.setTime(Long.parseLong(time_string));
    }

    public double getGramsAlcohol(boolean downtime){
        double percentRemaining = (COMPLETE_HOUR - checkTime()) / COMPLETE_HOUR;
        double peakTimeRemaining = 0.625;
        double peakTimePast = 0.375;
        double grams = STANDARD_DRINK_GRAMS;
        if(percentRemaining > peakTimeRemaining){
            grams = (((percentRemaining-peakTimeRemaining)/peakTimePast) * STANDARD_DRINK_GRAMS);
        } else if(percentRemaining <= peakTimeRemaining && downtime) {
            grams = (percentRemaining > 0) ? ((percentRemaining/peakTimeRemaining) * STANDARD_DRINK_GRAMS) : ALCOHOL_DIFFUSED;
        }
        return grams;
    }

    public double checkTime(){
        return ((new Date()).getTime() - drink_time.getTime()) / MILLISECONDS_TO_HOURS;
    }
}
