public enum Token {
    NUMBER,
    PLUS,
    MINUS,
    MULTIPLY,
    UNARY,
    END;
    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
