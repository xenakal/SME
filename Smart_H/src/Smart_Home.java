public class Smart_Home {




        //Creation de la maison
        //Initialise Controlled devices
        Light bedroom_light = new Light("bedroom");
        Light living_light = new Light("living_1");
        Light living_light2 = new Light("living_2"); //une 2e lampe
        Light kitchen_light = new Light("kitchen");


        //init sensors
        MotionDetector bedroom_motion_detector = new MotionDetector();
        MotionDetector living_motion_detector = new MotionDetector();
        MotionDetector kitchen_motion_detector = new MotionDetector();



        //Creation des piece
        //bedroom
        Rooms bedroom = new Rooms();

        //living
        Rooms living = new Rooms();

        //kitchen
        Rooms kitchen = new Rooms();


        //init manger
        Lightmanager bedroom_lightmanager = new Lightmanager();
        Lightmanager living_lightmanager = new Lightmanager();
        Lightmanager open_kitchen_lightmanager = new Lightmanager();

    public Smart_Home(){

        //Lier les devises aux piece des piece
        //bedroom
        bedroom.addDevice(bedroom_light);
        //living
        living.addDevice(living_light);
        living.addDevice(living_light2);
        //kitchen
        kitchen.addDevice(kitchen_light);


        //init manger
        bedroom_lightmanager.add(bedroom_light);
        living_lightmanager.add(living);
        open_kitchen_lightmanager.add(living);        //allumerra la cuisine et le living
        open_kitchen_lightmanager.add(kitchen);      //allumerra la cuisine et le living


        //lier un sensor à une/des lightmanager
        bedroom_motion_detector.attach(bedroom_lightmanager);
        living_motion_detector.attach(living_lightmanager);
        kitchen_motion_detector.attach(open_kitchen_lightmanager);

    }
}
