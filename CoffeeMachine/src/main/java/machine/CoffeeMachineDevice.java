package machine;
class CoffeeMachineDevice {
    private int waterQuantity;
    private int milkQuantity;
    private int beansQuantity;
    private int cupsQuantity;
    private int moneyBalance;
    private MachineState state;

    private void setState(MachineState state) {
        this.state = state;
        switch (state) {
            case CHOOSING_ACTION -> System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            case CHOOSING_COFFEE_TYPE ->
                    System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
            case FILL_WATER -> System.out.println("\nWrite how many ml of water you want to add:");
            case FILL_MILK -> System.out.println("Write how many ml of milk you want to add:");
            case FILL_BEANS -> System.out.println("Write how many grams of coffee beans you want to add:");
            case FILL_CUPS -> System.out.println("Write how many disposable cups you want to add:");
        }
    }

    public CoffeeMachineDevice() {
        this(400, 540, 120, 9, 550);
    }

    public CoffeeMachineDevice(int waterQuantity, int milkQuantity, int beansQuantity, int cupsQuantity, int moneyBalance) {
        this.waterQuantity = waterQuantity;
        this.milkQuantity = milkQuantity;
        this.beansQuantity = beansQuantity;
        this.cupsQuantity = cupsQuantity;
        this.moneyBalance = moneyBalance;
        this.setState(MachineState.CHOOSING_ACTION);

    }

    public void processUserInput(String input) {
        switch (state) {
            case CHOOSING_ACTION -> processChooseAction(input);
            case CHOOSING_COFFEE_TYPE -> processCoffeeTypeSelection(input);
            case FILL_WATER -> addWater(input);
            case FILL_BEANS -> addBeans(input);
            case FILL_CUPS -> addCups(input);
            case FILL_MILK -> addMilk(input);
        }
    }

    private void addWater(String input) {
        try {
            waterQuantity += Integer.parseInt(input);
            this.setState(MachineState.FILL_MILK);
        } catch (Exception ex) {
            System.out.println("Wrong input. Enter correct number string");
        }
    }

    private void addMilk(String input) {
        try {
            milkQuantity += Integer.parseInt(input);
            this.setState(MachineState.FILL_BEANS);
        } catch (Exception ex) {
            System.out.println("Wrong input. Enter correct number string");
        }
    }

    private void addBeans(String input) {
        try {
            beansQuantity += Integer.parseInt(input);
            this.setState(MachineState.FILL_CUPS);
        } catch (Exception ex) {
            System.out.println("Wrong input. Enter correct number string");
        }
    }

    private void addCups(String input) {
        try {
            cupsQuantity += Integer.parseInt(input);
            this.setState(MachineState.CHOOSING_ACTION);
        } catch (Exception ex) {
            System.out.println("Wrong input. Enter correct number string");
        }
    }

    private void printState() {
        System.out.printf("""
                                                
                        The coffee machine has:
                        %d ml of water
                        %d ml of milk\s
                        %d g of coffee beans\s
                        %d disposable cups\s
                        $%d of money
                        """,
                waterQuantity, milkQuantity, beansQuantity, cupsQuantity, moneyBalance);
        this.setState(MachineState.CHOOSING_ACTION);
    }

    //Обработка ввода из режима действия
    private void processChooseAction(String input) {
        switch (input) {
            case "buy" -> {
                this.setState(MachineState.CHOOSING_COFFEE_TYPE);
            }
            case "fill" -> {
                this.setState(MachineState.FILL_WATER);
            }
            case "take" -> {
                takeMoney();
            }
            case "remaining" -> {
                printState();
            }
            case "exit" -> {
                this.setState(MachineState.EXIT);
                System.exit(0);
            }
            default -> {
                System.out.println("Wrong input");
                this.setState(MachineState.CHOOSING_ACTION);
            }
        }
    }

    //Обработка ввода из меню покупки кофе
    private void processCoffeeTypeSelection(String input) {
        switch (input) {
            case "1" -> {
                makeCoffee(250, 0, 16, 4);
            }
            case "2" -> {
                makeCoffee(350, 75, 20, 7);
            }
            case "3" -> {
                makeCoffee(200, 100, 12, 6);
            }
            case "back" -> {
                this.setState(MachineState.CHOOSING_ACTION);
            }
            default -> {
                System.out.println("Wrong input");
                this.setState(MachineState.CHOOSING_COFFEE_TYPE);
            }
        }

    }

    private void makeCoffee(int requiredWater, int requiredMilk, int requiredBeans, int additionalMoney) {
        boolean isError = false;
        if (this.waterQuantity - requiredWater < 0) {
            System.out.println("Sorry, not enough water!");
            isError = true;
        }
        if (this.milkQuantity - requiredMilk < 0) {
            System.out.println("Sorry, not enough milk!");
            isError = true;
        }
        if (this.beansQuantity - requiredBeans < 0) {
            System.out.println("Sorry, not enough beans!");
            isError = true;
        }

        if (this.cupsQuantity == 0) {
            System.out.println("Sorry, not enough cups!");
            isError = true;
        }

        if (!isError) {
            System.out.println("I have enough resources, making you a coffee!");
            waterQuantity = waterQuantity - requiredWater;
            milkQuantity = milkQuantity - requiredMilk;
            beansQuantity = beansQuantity - requiredBeans;
            moneyBalance = moneyBalance + additionalMoney;
            cupsQuantity--;
        }
        this.setState(MachineState.CHOOSING_ACTION);
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d\n", moneyBalance);
        moneyBalance = 0;
        this.setState(MachineState.CHOOSING_ACTION);
    }
}