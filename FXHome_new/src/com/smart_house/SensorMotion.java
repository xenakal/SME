package com.smart_house;

public class SensorMotion extends AbsSensor {

    private int movement; // if movement is detected (0==false)

    @Override
    public Enum.Sensor getType() {
        return Enum.Sensor.motion;
    }

    public SensorMotion(String name){
        super(name);
        //this.name = name;
        movement = 0;
    }

    public void reset() {
        //TODO
    }

    public void sensor_on(){
        System.out.println("Motion Detector is on");
    }

    public void sensor_off(){
        movement = 0;
        System.out.println("Motion Detector is off");
        this.advertise();
    }

    public void detect(int value){
        if(value!=movement) {
            movement = value;
            this.advertise();
        }
    }


    public Info makeinfo(){
        return new Info("motion",  movement);
    }

    @Override
    public String toString(){
        return "      Motion detector : " + super.toString();
    }

}

