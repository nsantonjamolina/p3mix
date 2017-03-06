package com.example.ignaciosantonjamolina.p3;

/**
 * Created by ignaciosantonjamolina on 6/3/17.
 */

public class Scores {
    private String name;

    private String longitude;

    private String latitude;

    private String scoring;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getScoring ()
    {
        return scoring;
    }

    public void setScoring (String scoring)
    {
        this.scoring = scoring;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", longitude = "+longitude+", latitude = "+latitude+", scoring = "+scoring+"]";
    }
}
