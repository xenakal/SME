package com.smart_house;

public class SensorThermo extends AbsSensor{

    private int recorded_temp = 25;

    @Override
    public Enum.Sensor getType() {
        return Enum.Sensor.temperature;
    }

    protected SensorThermo(String name) {
        super(name);
     }

    public void sensor_on(){
        System.out.println("SensorThermo is on");
    }
    public void sensor_off(){
        System.out.println("SensorThermo is off");
    }

    void reset() {}

    public void detect(int value) {
        //System.out.println("# Température détectée: "+value);
        recorded_temp = value;
        this.advertise();
    }

    public Info makeinfo(){
        return new Info("temperature",  recorded_temp);
    }

    public Integer getManagerRequiredTemp(){
        try{
            ManagerThermo man = (ManagerThermo) obsList.get(0);
            return man.getRequired_temperature();
        }catch (Exception e){
            return  null ;
        }
    }
}
