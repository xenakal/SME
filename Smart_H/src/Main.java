public class Main {

    public static void main (String[] args) {
        Smart_Home smart = Smart_Home.getSmartHome();

        smart.smartHomeInit("/home/xenakis/Documents/Cours/Master/Q7/SME/repo/Smart_H/src/config.json5");
        System.out.println(smart.toString());

        Command.start();

        System.out.println("Merci d'avoir utilisé la Smart_Home !!");
    }
}

