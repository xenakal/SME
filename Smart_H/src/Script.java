public class Script {

    public static void main(String[] args){
        Smart_Home smart = Smart_Home.getSmartHome();

        smart.smartHomeInit("/config.json5");
        //Smart_Home smart = new Smart_Home("config.json5");
        System.out.println(smart.toString());


        //Creation de la maison
        //Initialise Controlled devices
        ActuLight bedroom_light = new ActuLight("bedroom");
        ActuLight living_light = new ActuLight("living_1");
        ActuLight living_light2 = new ActuLight("living_2"); //une 2e lampe
        ActuLight kitchen_light = new ActuLight("kitchen");
        ActuCoffeeMachine coffee = new ActuCoffeeMachine("What else ...");
        ActuRadiator living_radiator1 = new ActuRadiator("living1");
        ActuRadiator living_radiator2 = new ActuRadiator("living2");


        //init sensors
        SensorMotion bedroom_motion_detector = new SensorMotion("bedroom_detector");
        SensorMotion living_motion_detector = new SensorMotion("living_detector");
        SensorMotion kitchen_motion_detector = new SensorMotion("kitchen_detector");
        SensorThermo living_sensorThermo = new SensorThermo("living_sensorThermo");


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
        ManagerLight bedroom_lightmanager = new ManagerLight();
        ManagerLight living_lightmanager = new ManagerLight();
        ManagerLight open_kitchen_lightmanager = new ManagerLight();
        ManagerCoffee managerCoffee = new ManagerCoffee();
        ManagerThermo living_heatmanager = new ManagerThermo(25,1);
        bedroom_lightmanager.add(bedroom);
        living_lightmanager.add(living);
        open_kitchen_lightmanager.add(living);        //allumerra la cuisine et le living
        open_kitchen_lightmanager.add(kitchen);      //allumerra la cuisine et le living
        managerCoffee.add(kitchen);
        living_heatmanager.add(living_radiator1);
        living_heatmanager.add(living_radiator2);

        //lier un sensor à une/des lightmanager
        bedroom_motion_detector.attach(bedroom_lightmanager);
        living_motion_detector.attach(living_lightmanager);
        kitchen_motion_detector.attach(open_kitchen_lightmanager);
        kitchen_motion_detector.attach(managerCoffee);
        living_sensorThermo.attach(living_heatmanager);



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
        living_sensorThermo.sensor_on(); // senseur allumé: "sensor is on"

        System.out.println("# Il commence à faire nuit et la température baisse");
        living_sensorThermo.detect(20);
        living_sensorThermo.detect(23);
        living_sensorThermo.detect(26);
        System.out.println("# Mais il y a une fête qui commence, des gens se rassemblent et la piece chauffe");
        living_sensorThermo.detect(28); // mark bouge


        System.out.println("# Les gens partent la soirée finie, il commence à faire froid");
        living_sensorThermo.detect(23);//mark ne bouge plus
        System.out.println("# Le matin arrive, la température monte");
        living_sensorThermo.detect(27);// mark quitte la piece

        System.out.println("# On veut sauver la planette, on éteint le système de chauffage !");

        living_sensorThermo.sensor_off(); //Mark eteint le sensor
        living_heatmanager.all_off();
        System.out.println();
        System.out.println();
        // TODO: est ce qu'il faut que le chauffage ferme aussi?

    }

}