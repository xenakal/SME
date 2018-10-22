public class Light {

    String name;
    public boolean on = false;

    public Light(String name) {
        this.name = name;
    }

    public void turn_on(){
        if(!on){
            on = true;
            System.out.println(name + "Light on !");
        }
    }

    public void turn_off(){
        if(on){
            on = false;
            System.out.println(name + "Light off !");
        }
    }
}
