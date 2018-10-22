public class Script {

    public static void waitt(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){

        //Creation de la maison
        //Initialise Controlled devices
        Light bedroom_light = new Light("bedroom");
        Light living_light = new Light("living_1");
        Light living_light2 = new Light("living_2"); //une 2e lampe
        Light kitchen_light = new Light("kitchen");
        CoffeeMachine coffee = new CoffeeMachine("What else ...");


        //init sensors
        MotionDetector bedroom_motion_detector = new MotionDetector();
        MotionDetector living_motion_detector = new MotionDetector();
        MotionDetector kitchen_motion_detector = new MotionDetector();



        //Creation des piece
        //bedroom
        Rooms bedroom = new Rooms();
        bedroom.addDevice(bedroom_light);
        //living
        Rooms living = new Rooms();
        living.addDevice(living_light);
        living.addDevice(living_light2);
        //kitchen
        Rooms kitchen = new Rooms();
        kitchen.addDevice(kitchen_light);
        kitchen.addDevice(coffee);


        //init manger
        Lightmanager bedroom_lightmanager = new Lightmanager();
        Lightmanager living_lightmanager = new Lightmanager();
        Lightmanager open_kitchen_lightmanager = new Lightmanager();
        CoffeeManager coffeeManager = new CoffeeManager();
        bedroom_lightmanager.add(bedroom);
        living_lightmanager.add(living);
        open_kitchen_lightmanager.add(living);        //allumerra la cuisine et le living
        open_kitchen_lightmanager.add(kitchen);      //allumerra la cuisine et le living
        coffeeManager.add(kitchen);

        //lier un sensor à une/des lightmanager
        bedroom_motion_detector.attach(bedroom_lightmanager);
        living_motion_detector.attach(living_lightmanager);
        kitchen_motion_detector.attach(open_kitchen_lightmanager);
        kitchen_motion_detector.attach(coffeeManager);


        //Smart_Home sh = new Smart_Home();



        System.out.println("First Test with bedroom");
        bedroom_motion_detector.sensor_on(); // senseur allumé: "sensor is on" (run en boucle jusqu'au sensor_off)

        System.out.println("# Mark rentre dans la piece");
        bedroom_motion_detector.detect(1); // mark rentre dans la piece
        waitt(1000);
        System.out.println("Mark bouge");
        bedroom_motion_detector.detect(1); // mark bouge
        waitt(1000);
        //light on

        System.out.println("Mark bouge plus");
        bedroom_motion_detector.detect(0);//mark ne bouge plus
        waitt(1000);
        System.out.println("Mark bouge plus");
        bedroom_motion_detector.detect(0);//mark ne bouge plus
        waitt(1000);
        System.out.println("Mark bouge");
        bedroom_motion_detector.detect(1);//mark rebouge
        waitt(2000);
        System.out.println("Mark quitte la piece");
        bedroom_motion_detector.detect(0);// mark quitte la piece
        waitt(11112);
        //light off
        System.out.println("Mark rentre dans la piece");
        bedroom_motion_detector.detect(1); // Mark rentre dans la piece
        waitt(2000);

        bedroom_motion_detector.sensor_off(); //Mark eteint le sensor
        System.out.println();
        System.out.println();

        waitt(50000);


        System.out.println("Second scenario: Un petit tour de la maison");
        System.out.println("Allumage des sensors");
        bedroom_motion_detector.sensor_on();
        living_motion_detector.sensor_on();
        kitchen_motion_detector.sensor_on();

        System.out.println("Bob wake up and move in the bedroom");
        bedroom_motion_detector.detect(1);
        waitt(2000);
        System.out.println("Bob goes to the kitchen");
        waitt(4000);
        bedroom_motion_detector.detect(0);
        kitchen_motion_detector.detect(1);
        waitt(2000);
        System.out.println("Bob drink the coffee");
        waitt(2000);
        System.out.println("Bob goes in the living room");
        kitchen_motion_detector.detect(0);
        living_motion_detector.detect(1);
        waitt(4000);

        System.out.println("Extinction des sensors");
        bedroom_motion_detector.sensor_off();
        living_motion_detector.sensor_off();
        kitchen_motion_detector.sensor_off();

    }

}