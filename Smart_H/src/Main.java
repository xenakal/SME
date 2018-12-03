public class Main {

    public static void main (String[] args) {
        String fileName = "/Users/DimiS/Documents/Maintenace & evolution M1Q1/SME/Smart_H/src/config.json5";
        SmartHome smart = SmartHome.getSmartHome();
        smart.smartHomeInit(fileName);
        //System.out.println(smart.toString());


        Param param = Param.getInstance();
        param.adaptToConfig(fileName);
        System.out.println(smart.toString());

        Command.start();

        System.out.println("Merci d'avoir utilisé la SmartHome !!");
    }
}

