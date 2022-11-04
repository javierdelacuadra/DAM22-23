package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("A por el boteeeee");
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);
        Fecha fecha = new Fecha();
        panel.add(fecha, BorderLayout.NORTH);
        JButton button = new JButton("Comprobar fecha");
        panel.add(button, BorderLayout.SOUTH);
        panel.updateUI();
        frame.setVisible(true);
        frame.setSize(300, 300);
        button.setBounds(10, 110, 80, 25);
        button.setSize(165, 25);
        button.addActionListener(e -> {
            try {
                fecha.getFecha();
                JOptionPane.showMessageDialog(null, "Fecha correcta");
            } catch (FechaException fe) {
                JOptionPane.showMessageDialog(frame, fe.getMessage());
            }
        });
        Fecha fecha2 = new Fecha();
        try {
            fecha2.setFecha(27, 2, 1900);
        } catch (FechaException e) {
            throw new RuntimeException(e);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}