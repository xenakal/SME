public class ActuCoffeeMachine implements Actuator{

    String name;
    public boolean isMakingCoffee = false;


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
            return "coffee machine : " +name + "  ";
        }
}
