public class ActuLight extends Object implements Actuator{

    String name;
    public boolean on = false;

    public ActuLight(String name) {
        this.name = name;
    }

    @Override
    public Enum.Actuator getType() {
        return Enum.Actuator.light;
    }


    public void turn_on(){
        if(!on){
            on = true;
            System.out.println(name + "ActuLight on !");
        }
    }

    public void turn_off(){
        if(on){
            on = false;
            System.out.println(name + "ActuLight off !");
        }
    }

    public String toString(){
        return "light : " +name + "  ";
    }
}
