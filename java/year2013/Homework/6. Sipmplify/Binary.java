public abstract class Binary implements Expression3 {
    private Expression3 a, b;
    private String operation;
    private int priority;

    public Binary(Expression3 a, Expression3 b, String operation) {
        this.a = a;
        this.b = b;
        this.operation = operation;
        this.priority = getPriority();

    }

    public String getOperation() {
        return this.operation;
    }

    public int getPriority() {
        if (this.operation.equals("+") || this.operation.equals("-"))
            return 0;
        else
            return 1;
    }

    public String toString1() {
        String s1;
        String s2;

        if (a.getPriority() < this.priority) {
            s1 = "(" + a.toString1() + ")";
        } else
            s1 = a.toString1();

        if (b.getPriority() < this.priority) {
            s2 = "(" + b.toString1() + ")";
        } else
            s2 = b.toString1();

        return s1 + this.operation + s2;


    }

    public String simplify() {
        String s1, s2;

        if (this.operation.equals("+")) {
            if (Character.isDigit(a.getOperation().charAt(0)) && Character.isDigit(b.getOperation().charAt(0))) {
                int tmt = Integer.parseInt(a.getOperation()) + Integer.parseInt(b.getOperation());

                return String.valueOf(tmt);
            }
            if (a.getOperation().equals("0")) {
                return b.simplify();
            }
            if (b.getOperation().equals("0")) {
                return a.simplify();
            }
        }

        if (this.operation.equals("-")) {
            if (Character.isDigit(a.getOperation().charAt(0)) && Character.isDigit(b.getOperation().charAt(0))) {
                int tmt = Integer.parseInt(a.getOperation()) - Integer.parseInt(b.getOperation());

                return String.valueOf(tmt);
            }
            if (a.getOperation().equals("0")) {
                return b.simplify();
            }
            if (b.getOperation().equals("0")) {
                return a.simplify();
            }
        }


        if (this.operation.equals("*")) {
            if (Character.isDigit(a.getOperation().charAt(0)) && Character.isDigit(b.getOperation().charAt(0))) {
                int tmt = Integer.parseInt(a.getOperation()) * Integer.parseInt(b.getOperation());

                return String.valueOf(tmt);
            }
            if (a.getOperation().equals("0")) {
                return "0";
            }
            if (b.getOperation().equals("0")) {
                return "0";
            }
            if (a.getOperation().equals("1")) {
                return b.simplify();
            }
            if (b.getOperation().equals("1")) {
                return a.simplify();
            }
        }

        if (this.operation.equals("/")) {
            if (Character.isDigit(a.getOperation().charAt(0)) && Character.isDigit(b.getOperation().charAt(0))) {
                int tmt = Integer.parseInt(a.getOperation()) / Integer.parseInt(b.getOperation());

                return String.valueOf(tmt);
            }
            if (a.getOperation().equals("0")) {
                return "0";
            }
            if (b.getOperation().equals("0")) {
               throw new ArithmeticException();
            }
            if (b.getOperation().equals("1")) {
                return a.simplify();
            }
        }


        return toString1();
    }

    public int evaluate(int x, int y, int z) {
        int c1 = 1;
        int c2 = 1;
        c1 = a.evaluate(x, y, z);
        c2 = b.evaluate(x, y, z);
        return run(c1, c2);
    }

    protected abstract int run(int c1, int c2);

}