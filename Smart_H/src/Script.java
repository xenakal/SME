public class Script {

    public static void main(String[] args){
        //Smart_Home smart = Smart_Home.getSmartHome();

        //smart.Smart_Home_Init("config.json5");
        Smart_Home smart = new Smart_Home("config.json5");
        System.out.println(smart.toString());


        //Creation de la maison
        //Initialise Controlled devices
        Light bedroom_light = new Light("bedroom");
        Light living_light = new Light("living_1");
        Light living_light2 = new Light("living_2"); //une 2e lampe
        Light kitchen_light = new Light("kitchen");
        CoffeeMachine coffee = new CoffeeMachine("What else ...");
        Radiator living_radiator1 = new Radiator("living1");
        Radiator living_radiator2 = new Radiator("living2");


        //init sensors
        MotionDetector bedroom_motion_detector = new MotionDetector("bedroom_detector");
        MotionDetector living_motion_detector = new MotionDetector("living_detector");
        MotionDetector kitchen_motion_detector = new MotionDetector("kitchen_detector");
        Thermometer living_thermometer = new Thermometer("living_thermometer");


        //Creation des piece
        //bedroom
        Rooms bedroom = new Rooms();
        bedroom.addDevice(bedroom_light);
        //living
        Rooms living = new Rooms();
        living.addDevice(living_light);
        living.addDevice(living_light2);
        living.addDevice(living_radiator1);
        living.addDevice(living_radiator2);
        //kitchen
        Rooms kitchen = new Rooms();
        kitchen.addDevice(kitchen_light);
        kitchen.addDevice(coffee);


        //init manager
        Lightmanager bedroom_lightmanager = new Lightmanager();
        Lightmanager living_lightmanager = new Lightmanager();
        Lightmanager open_kitchen_lightmanager = new Lightmanager();
        CoffeeManager coffeeManager = new CoffeeManager();
        Thermostat_Manager living_heatmanager = new Thermostat_Manager(25,1);
        bedroom_lightmanager.add(bedroom);
        living_lightmanager.add(living);
        open_kitchen_lightmanager.add(living);        //allumerra la cuisine et le living
        open_kitchen_lightmanager.add(kitchen);      //allumerra la cuisine et le living
        coffeeManager.add(kitchen);
        living_heatmanager.add(living_radiator1);
        living_heatmanager.add(living_radiator2);

        //lier un sensor à une/des lightmanager
        bedroom_motion_detector.attach(bedroom_lightmanager);
        living_motion_detector.attach(living_lightmanager);
        kitchen_motion_detector.attach(open_kitchen_lightmanager);
        kitchen_motion_detector.attach(coffeeManager);
        living_thermometer.attach(living_heatmanager);



        System.out.println("# First Test with bedroom");
        bedroom_motion_detector.sensor_on(); // senseur allumé: "sensor is on" (run en boucle jusqu'au sensor_off)

        System.out.println("# Mark rentre dans la piece");
        bedroom_motion_detector.detect(1); // mark rentre dans la piece
        System.out.println("# Mark bouge");
        bedroom_motion_detector.detect(1); // mark bouge
        //light on

        System.out.println("# Mark bouge plus");
        bedroom_motion_detector.detect(0);//mark ne bouge plus
        System.out.println("# Mark bouge plus");
        bedroom_motion_detector.detect(0);//mark ne bouge plus
        System.out.println("# Mark bouge");
        bedroom_motion_detector.detect(1);//mark rebouge
        System.out.println("# Mark quitte la piece");
        bedroom_motion_detector.detect(0);// mark quitte la piece
        //light off
        System.out.println("# Mark rentre dans la piece");
        bedroom_motion_detector.detect(1); // Mark rentre dans la piece

        bedroom_motion_detector.sensor_off(); //Mark eteint le sensor
        System.out.println();
        System.out.println();

        //waitt(5000);


        System.out.println("# Second scenario: Un petit tour de la maison");
        System.out.println("# Allumage des sensors");
        bedroom_motion_detector.sensor_on();
        living_motion_detector.sensor_on();
        kitchen_motion_detector.sensor_on();

        System.out.println("# Bob wake up and move in the bedroom");
        bedroom_motion_detector.detect(1);
        System.out.println("# Bob goes to the kitchen");
        bedroom_motion_detector.detect(0);
        kitchen_motion_detector.detect(1);
        System.out.println("# Bob drink the coffee");
        System.out.println("# Bob goes in the living room");
        kitchen_motion_detector.detect(0);
        living_motion_detector.detect(1);
        //waitt(4000);

        System.out.println("# Extinction des sensors");
        bedroom_motion_detector.sensor_off();
        living_motion_detector.sensor_off();
        kitchen_motion_detector.sensor_off();

        //waitt(3000);
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("# Scenario with Heat control");
        System.out.println("## La température à maintenir est de 25 °C, à 1 °C près");
        living_thermometer.sensor_on(); // senseur allumé: "sensor is on"

        System.out.println("# Il commence à faire nuit et la température baisse");
        living_thermometer.detect(20);
        living_thermometer.detect(23);
        living_thermometer.detect(26);
        System.out.println("# Mais il y a une fête qui commence, des gens se rassemblent et la piece chauffe");
        living_thermometer.detect(28); // mark bouge


        System.out.println("# Les gens partent la soirée finie, il commence à faire froid");
        living_thermometer.detect(23);//mark ne bouge plus
        System.out.println("# Le matin arrive, la température monte");
        living_thermometer.detect(27);// mark quitte la piece

        System.out.println("# On veut sauver la planette, on éteint le système de chauffage !");

        living_thermometer.sensor_off(); //Mark eteint le sensor
        living_heatmanager.all_off();
        System.out.println();
        System.out.println();
        // TODO: est ce qu'il faut que le chauffage ferme aussi?

    }

}