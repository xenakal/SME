public class Script {

    public static void waitt(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){

        //Initialise Controlled devices
        Light living_light = new Light();
        Light living_light2 = new Light(); //une 2e lampe

        //init manger
        Lightmanager lightmanager = new Lightmanager();
        lightmanager.addLight(living_light);
        lightmanager.addLight(living_light2);

        //init sensors
        MotionDetector light_sensor = new MotionDetector(living_light);
        light_sensor.attach(lightmanager);


        Smart_Home sh = new Smart_Home(light_sensor,living_light);

        light_sensor.sensor_on(); // senseur allumé: "sensor is on" (run en boucle jusqu'au sensor_off)

        light_sensor.detect(1); // movement = true

        waitt(10002);
        light_sensor.detect(1);


        waitt(900);
        light_sensor.sensor_off();

        light_sensor.sensor_on();

        light_sensor.detect(1);
        waitt(1000);
        light_sensor.sensor_off();

    }

}
