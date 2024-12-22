package bsu.rfct.java.course2.group7.Krutalevich.lab3;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PolynomialTable {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Таблица многочленов");
        HornerScheme horner = new HornerScheme();

        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Справка");
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Автор: Круталевич А.А., группа: 7", "О программе", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        List<PolynomialData> data = new ArrayList<>();
        data.add(new PolynomialData(horner.evaluatePolynomial(2),2));
        data.add(new PolynomialData(horner.evaluatePolynomial(0.1),0.1));
        data.add(new PolynomialData(horner.evaluatePolynomial(1.2),1.2));

        PolynomialTableModel model = new PolynomialTableModel(data);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
