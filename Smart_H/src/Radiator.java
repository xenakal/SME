public class Radiator {

    String name;
    private boolean on = false;

    public Radiator(String name){this.name = name;}

    public void turn_on(){
        if(!on){
            on = true;
            System.out.println(name + ": Radiator on !");
        }
    }

    public void turn_off(){
        if(on){
            on = false;
            System.out.println(name + ": Radiator off !");
        }
    }

    public void increase_heat(){
        System.out.println("radiator "+name+" increases heat");
    }

    public void decrease_heat(){
        System.out.println("radiator "+name+" decreases heat");
    }
    public boolean getState(){
        return on;
    }

    public String toString(){
        return "radiator : " +name + "  ";
    }


}
