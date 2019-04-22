package com.codercoral.electricitychart.chart.entity;

public class ChartEntity {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    private String name;
    private int imageId;

    public ChartEntity(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }


}
