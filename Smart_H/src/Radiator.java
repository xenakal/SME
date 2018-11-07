public class Radiator {

    String name;
    public boolean on = false;
    private int temp;

    public Radiator(String name){this.name = name; temp = 25;}

    public void setTemp(int value){
        if(on){
            if(value < temp){
                System.out.println("decreasing heat");
            }
            else if(value > temp){
                System.out.println("Increasing heat");
            }
            else{
                System.out.println("already at good temperature");
            }
            temp = value;
        }
        else{
            System.out.println("seems the radiator is off !");
        }
    }

}
