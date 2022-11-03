package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Fecha fecha = new Fecha();
        JButton button = new JButton("Show calendar");
        fecha.panel.add(button);
        button.addActionListener(e -> {
            try {
                fecha.getFecha();
            } catch (FechaException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}