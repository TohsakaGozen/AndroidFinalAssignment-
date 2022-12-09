package com.exmple.communityepidemicassistant.BookingPackage;

public class Book {
    private String name;
    private String time;
    private String site;

    public Book(String name, String time, String site) {
        this.name = name;
        this.time = time;
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }


}
