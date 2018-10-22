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
        Light living_light = new Light("living_1");
        //Light living_light2 = new Light("living_2"); //une 2e lampe

        //init manger
        Lightmanager lightmanager = new Lightmanager();
        lightmanager.addLight(living_light);
        //lightmanager.addLight(living_light2);

        //init sensors
        MotionDetector light_sensor = new MotionDetector();
        light_sensor.attach(lightmanager);


        Smart_Home sh = new Smart_Home(light_sensor,living_light);

        light_sensor.sensor_on(); // senseur allumé: "sensor is on" (run en boucle jusqu'au sensor_off)
        System.out.println("detect1");
        light_sensor.detect(1);
        waitt(5000);
        System.out.println("detect0");
        light_sensor.detect(0);
        waitt(15000);
        light_sensor.sensor_off();


        /*
        light_sensor.sensor_on();
        System.out.println("Mark rentre dans la piece");
        light_sensor.detect(1); // mark rentre dans la piece
        waitt(1000);
        System.out.println("Mark bouge");
        light_sensor.detect(1); // mark bouge
        waitt(1000);
        //light on

        System.out.println("Mark bouge plus");
        light_sensor.detect(0);//mark ne bouge plus
        waitt(10000);
        System.out.println("Mark bouge plus");
        light_sensor.detect(0);//mark ne bouge plus
        waitt(10000);
        System.out.println("Mark bouge");
        light_sensor.detect(1);//mark rebouge
        waitt(2000);
        System.out.println("Mark quitte la piece");
        light_sensor.detect(0);// mark quitte la piece
        waitt(10512);
        //light off
        System.out.println("Mark rentre dans la piece");
        light_sensor.detect(1); // Mark rentre dans la piece
        waitt(1000);

        waitt(9999);
        light_sensor.sensor_off(); //Mark eteint le sensor
*/


    }

}
