package com.smart_house;

import java.util.LinkedList;
import java.util.List;

public abstract class AbsSensor extends Object{

    protected String name;
    protected List<ManagerFeature> obsList ;
    protected Boolean isActive = false;


    protected AbsSensor(String name){
        this.name = name;
        obsList = new LinkedList<ManagerFeature>();

    }

    public String getName() {
        return name;
    }
    public abstract Enum.Sensor getType();

    public void setName(String name) {
        this.name = name;
    }
    public boolean isActive() {
        return this.isActive;
    }
    public void active(){
        if(!isActive){
            isActive = true;
            System.out.println("sensor " + this.getName()+  " is activate ");
            advertise();
        }
    }
    public void deactive(){
        if(isActive){
            isActive = false;
            System.out.println("sensor " + this.getName()+  " is deactivate ");
        }
    }

    abstract void reset();
    abstract void detect(int value);
    abstract Info makeinfo();


    public String toString(){
        String str = this.name ;
        if(this.isActive()) str+= " is active and "; else str+= " is not active and ";
        str +=  " is connected to \n";
        for (ManagerFeature m: obsList) {
            str = str + m.ToString() + "\n";
        }
        return str;
    }



    public void sensor_on(){
        System.out.println("#sensor is on");
    }


    public void sensor_off(){
        System.out.println("#sensor is off");
    }

    public void attach(ManagerFeature obs){
        if (!this.obsList.contains(obs)){
            this.obsList.add(obs);
        }
    }

    public void detach(ManagerFeature obs){
        obsList.remove(obs);
    }
    public void advertise(){
        if(this.isActive()) {
            //System.out.println("advertise");
            for (ManagerFeature o : obsList) {
                o.react(this.makeinfo());
            }
        }
    }
}
