public class CoffeeMachine {

        String name;
        public boolean isMakingCoffee = false;

        public CoffeeMachine(String name) {
            this.name = name;
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
