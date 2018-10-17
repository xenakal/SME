public class Light {

    String name;
    public boolean on = false;

    public Light(String name) {
        this.name = name;
    }

    public void turn_on(){
        on = true;
        System.out.println(name + "Light on !");
    }

    public void turn_off(){
        on = false;
        System.out.println(name + "Light off !");
    }



}
