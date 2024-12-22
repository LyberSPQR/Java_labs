package bsu.rfct.java.course2.group7.Krutalevich.lab1.food;

public class Potato extends Food {
    private final String size;
    public Potato(String size) {
        super("Potato");
        this.size = size;
    }

    @Override
    public void consume() {
        System.out.println(this + " was eaten");
    }

    @Override
    public String toString() {
        return "'" + size + "' " + super.toString();
    }
}
