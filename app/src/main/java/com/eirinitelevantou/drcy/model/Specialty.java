package com.eirinitelevantou.drcy.model;

/**
 * Created by eirini.televantou on 07/12/2017.
 */

public class Specialty {

    public String name;
    public int id;
    public int drawable;

    public Specialty( int id, String name, int drawable) {
        this.name = name;
        this.id = id;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
