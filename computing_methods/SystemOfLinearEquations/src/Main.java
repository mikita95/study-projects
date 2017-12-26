import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int size = 1000;
        Matrix A = new Matrix(size, size);
        Vector b = new Vector((int)size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            b.set(i, random.nextDouble());
            for (int k = 0; k < size; k++)
                A.set(i, k, random.nextDouble());
        }
        //Matrix A = new Matrix(new double[]{7, -2, -1}, new double[]{6, -4, -5}, new double[]{1, 2, 4});
        //Vector b = new Vector(2, 3, 5);
        long t = System.currentTimeMillis();

       // Vector answer = new JacobiMethod(100).solve(new LinearEquationsSystem(A, b), 1e-8);
        t = System.currentTimeMillis() - t;
        System.out.println(t / 1000.);
        //System.out.print(answer);
    }
}
