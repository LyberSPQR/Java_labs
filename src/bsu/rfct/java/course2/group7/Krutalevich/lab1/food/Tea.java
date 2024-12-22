package bsu.rfct.java.course2.group7.Krutalevich.lab1.food;

public class Tea extends Food {
    private final String size;
    public Tea(String size) {
        super("Tea");
        this.size = size;
    }

    @Override
    public void consume() {
        System.out.println(this + " was drunk");
    }

    @Override
    public String toString() {
        return "'" + size + "' " + super.toString();
    }
}
