package com.smart_house;

public class ActuCoffeeMachine implements Actuator {

    private String name;
    private boolean isMakingCoffee = false;
    private boolean isActive = false;

    public boolean isActive() {
        return isActive;
    }
    public void active(){
        isActive = true;
        System.out.println("# "+name + " activate");
    }
    public void deactive(){
        isActive = false;
        System.out.println("# "+name + " deactivate");
    }

    public ActuCoffeeMachine(String name) {
            this.name = name;
        }

    @Override
    public Enum.Actuator getType() {
        return Enum.Actuator.coffee;
    }


    public void makeCoffee(){
            if(!isMakingCoffee){
                isMakingCoffee = true;
                System.out.println("Coffee machine :" +name + "have made a coffee");
                isMakingCoffee = false;
            }
    }
    public String toString(){
        String acti = isActive()?" is active and ": " is not active and ";
            return "coffee machine : " +  acti +name + "  ";
    }
    
    public String getName() { return this.name; }
    
}
