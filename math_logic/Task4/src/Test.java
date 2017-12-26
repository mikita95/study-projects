public class Test {
    public static void main(String[] args) {
        String name = "./tests/correct";
        String name1 = "./tests/incorrect";
        for (int i = 3; i <= 13; i++) {
            Main.inputFile = name + i + ".in";
            Main.outputFile = name + i + ".out";
            run();
            System.out.println("TEST " + i + " HAS BEEN DONE");
        }

        System.out.println();

        for (int i = 1; i <= 10; i++) {
            Main.inputFile = name1 + i + ".in";
            Main.outputFile = name1 + i + ".out";
            run();
            System.out.println("TEST " + i + " HAS BEEN DONE");
        }
    }

    private static void run() {
        new Main().run();
    }
}
