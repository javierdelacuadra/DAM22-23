package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Main {
    public static void main(String[] args) throws FechaException {
        JFrame frame = new JFrame("Calendario");
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);
        Fecha fecha = new Fecha(1, 11, 2020);
        panel.add(fecha, BorderLayout.NORTH);
        JButton button = new JButton("Comprobar fecha");
        panel.add(button, BorderLayout.SOUTH);
        panel.updateUI();
        frame.setVisible(true);
        frame.setSize(300, 300);
        button.setBounds(10, 110, 80, 25);
        button.setSize(165, 25);
        Fecha fecha2 = new Fecha(1, 6, 2020);
        panel.add(fecha2, BorderLayout.CENTER);
        panel.updateUI();
        button.addActionListener(e -> {
            try {
                LocalDate date1 = fecha.getFecha();
                LocalDate date2 = fecha2.getFecha();
                long numeroDias = DAYS.between(date1, date2);
                numeroDias = Math.abs(numeroDias);
                JOptionPane.showMessageDialog(null, "Hay " + numeroDias + " días entre las dos fechas");

            } catch (FechaException fe) {
                JOptionPane.showMessageDialog(frame, fe.getMessage());
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}