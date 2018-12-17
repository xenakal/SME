package com.smart_house;

public class SensorButton extends AbsSensor{

    @Override
    public Enum.Sensor getType() {
        return Enum.Sensor.button;
    }

    public SensorButton(String name){
        super(name);
    }

    public void reset() {
    }

    public void sensor_on(){
        System.out.println("Motion Detector is on");
    }

    public void sensor_off(){
        System.out.println("Motion Detector is off");
        this.advertise();
    }

    public void detect(int value){
        this.advertise();
    }


    public Info makeinfo(){
        return new Info("button",  1);
    }

    @Override
    public String toString(){
        return "       Button detector : " + super.toString();
    }

}

