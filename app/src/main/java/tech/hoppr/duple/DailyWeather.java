package tech.hoppr.duple;

import java.time.DayOfWeek;

public class DailyWeather {
    private int mMinTemp;
    private int mMaxTemp;
    private String mDescription;
    private String mImageUrl;
    private DayOfWeek mWeekDay;


    public DailyWeather(int mMinTemp, int mMaxTemp, String mDescription, String mImageUrl, DayOfWeek mWeekDay) {
        this.mMinTemp = mMinTemp;
        this.mMaxTemp = mMaxTemp;
        this.mDescription = mDescription;
        this.mImageUrl = mImageUrl;
        this.mWeekDay = mWeekDay;
    }

    public int getmMinTemp() {
        return mMinTemp;
    }

    public void setmMinTemp(int mMinTemp) {
        this.mMinTemp = mMinTemp;
    }

    public int getmMaxTemp() {
        return mMaxTemp;
    }

    public void setmMaxTemp(int mMaxTemp) {
        this.mMaxTemp = mMaxTemp;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public DayOfWeek getmWeekDay() {
        return mWeekDay;
    }

    public void setmWeekDay(DayOfWeek mWeekDay) {
        this.mWeekDay = mWeekDay;
    }
}
