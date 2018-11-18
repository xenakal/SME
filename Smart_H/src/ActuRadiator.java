public class ActuRadiator implements Actuator{

    String name;
    private boolean on = false;

    public ActuRadiator(String name){this.name = name;}

    @Override
    public Enum.Actuator getType() {
        return Enum.Actuator.radiator;
    }


    public void turn_on(){
        if(!on){
            on = true;
            System.out.println("# " + name + ": ActuRadiator on !");
            System.out.println("# radiator "+name+" increases heat");
        }
    }

    public void turn_off(){
        if(on){
            on = false;
            System.out.println("# " + name + ": ActuRadiator off !");
        }
    }

    public boolean getState(){
        return on;
    }

    public String toString(){
        return "# "+ "radiator : " +name + "  ";
    }


}
