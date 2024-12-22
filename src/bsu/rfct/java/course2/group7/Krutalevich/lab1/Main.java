//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package bsu.rfct.java.course2.group7.Krutalevich.lab1;
import bsu.rfct.java.course2.group7.Krutalevich.lab1.food.*;
public class Main {


    public static void main(String[] args)
    {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        Food[] breakfast = new Food[10];
        int foodCounter = 0;
        int eggCounter = 0;
        int appleCounter = 0;
        int potatoCounter = 0;
        int teaCounter = 0;
        for (String arg:args)
        {
            String[] parts = arg.split("/");
            if(parts[0].equals("Egg"))
            {
                breakfast[foodCounter] = new Egg();
                eggCounter++;
            }
            if(parts[0].equals("Apple"))
            {
                breakfast[foodCounter] = new Apple(parts[1]);
                appleCounter++;
            }
            if(parts[0].equals("Potato"))
            {
                breakfast[foodCounter] = new Potato(parts[1]);
                potatoCounter++;
            }
            if(parts[0].equals("Tea"))
            {
                breakfast[foodCounter] = new Tea(parts[1]);
                teaCounter++;
            }
            foodCounter++;
        }

        System.out.println("List of products:");
        System.out.println("Egg" + ": " + eggCounter);
        System.out.println("Apple: " + appleCounter);
        System.out.println("Potato: " + potatoCounter);
        System.out.println("Tea: " + teaCounter);

        System.out.println("List of consumed products:");
        for (Food product: breakfast)
        {
            if(product != null)
            {
                product.consume();
            }
            else{
                break;
            }


        }
    }
}