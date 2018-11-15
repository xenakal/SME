public class ActuAlarm implements Actuator{

    String name;
    //public boolean isActivate = false;


    public ActuAlarm(String name) {
        this.name = name;
    }

    @Override
    public Enum.Actuator getType() {
        return Enum.Actuator.alarm;
    }


    public void Bip(){
            System.out.println("Beeeeeep");
    }


    public void callThePolice(){

    }

    public String toString(){
        return "alarm : " +name + "  ";
    }

    //TODO more complete
    //TODO Code, ...

}
