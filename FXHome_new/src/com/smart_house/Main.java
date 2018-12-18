package com.smart_house;

public class Main {

    public static void main (String[] args) {
        //String fileName = "./Smart_H/src/com/smart_house/config.json5";
        String fileName = "./src/com/smart_house/config.json5";
        SmartHome smart = SmartHome.getSmartHome();
        smart.smartHomeInit(fileName);
        //System.out.println(smart.toString());


        Param param = Param.getInstance();
        param.adaptToConfig(fileName);
        System.out.println(smart.toString());

        Client.start();

        System.out.println("Merci d'avoir utilisé la SmartHome !!");
    }
}
