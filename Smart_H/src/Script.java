public class Script {

    public static void waitt(){
        try {
            Thread.sleep(1000);
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
        Sensor light_sensor = new Sensor(living_light);
        light_sensor.attach(lightmanager);


        Smart_Home sh = new Smart_Home(light_sensor,living_light);

        light_sensor.sensor_on(); // senseur allumé: "sensor is on" (run en boucle jusqu'au sensor_off)

        light_sensor.detect(1); // movement = true  //jour

        waitt();
        light_sensor.detect(0); //nuit
        waitt();
        light_sensor.detect(0); //nuit
        waitt();
        light_sensor.detect(1); //jour
        waitt();

        light_sensor.sensor_off();



    }

}
