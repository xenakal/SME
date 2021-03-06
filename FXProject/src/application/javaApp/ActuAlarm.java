package application.javaApp;

public class ActuAlarm implements Actuator{

    String name;
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
        System.out.println("Call the police");
    }

    public String toString(){
        return "alarm : " +name + "  ";
    }

    public String getName() { return this.name; }
    //TODO more complete
    //TODO Code, ...

}
