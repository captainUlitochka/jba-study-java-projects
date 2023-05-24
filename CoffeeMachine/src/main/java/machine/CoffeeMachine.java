package machine;

import java.util.Scanner;
public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachineDevice machine = new CoffeeMachineDevice();
        String variant;

        do {
            variant = scanner.next();
            machine.processUserInput(variant);
        } while (true);
    }
}
