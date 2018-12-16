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

    public void detect_switch(){
        movement = 1 - movement;
        for (ManagerFeature o : obsList) {
            o.active(); // ici ca devrait pas poser problème, car si un bouton est appuyé, c'est qu'il est dans la configuartion (car sinon il n'apparait pas dans le projet fx, du coup comme c'est un projet séparé c'est bon)
        }
        this.advertise();
    }

    public Info makeinfo(){
        System.out.println("makeinfo");return new Info("motion",  movement);
    }

    @Override
    public String toString(){
        return "      Motion detector : " + super.toString();
    }

}

