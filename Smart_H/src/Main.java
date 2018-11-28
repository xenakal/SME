public class Main {

    public static void main (String[] args) {
        SmartHome smart = SmartHome.getSmartHome();

        smart.smartHomeInit("/home/xenakis/Documents/Cours/Master/Q7/SME/repo/Smart_H/src/config.json5");
        System.out.println(smart.toString());

        Client.start();

        System.out.println("Merci d'avoir utilisé la Smart_Home !!");
    }
}

