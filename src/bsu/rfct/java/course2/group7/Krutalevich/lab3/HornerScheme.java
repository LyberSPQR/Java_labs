package bsu.rfct.java.course2.group7.Krutalevich.lab3;
public class HornerScheme {

    public static double evaluatePolynomial(double x) {
        // Начинаем с последнего коэффициента (старший член)
        double result = 0;
        double[] coefficients = {0.2, 0.6, 0.4};

        // Проходим по всем коэффициентам с конца
        for (int i = coefficients.length - 1; i >= 0; i--) {
            result = result * x + coefficients[i];
        }
        return result;
    }
}