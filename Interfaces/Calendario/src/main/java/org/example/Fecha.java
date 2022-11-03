package org.example;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.LocalDate;

public class Fecha implements Calendariable {

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    JTextField textField1 = new JTextField(2);
    JTextField textField2 = new JTextField(2);
    JTextField textField3 = new JTextField(4);

    @Override
    public void showCalendar() {

        panel.add(textField1);
        panel.add(textField2);
        panel.add(textField3);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(200, 200);
        frame.setTitle("Calendario");
        frame.setResizable(false);
    }

    public Fecha() {
        showCalendar();
    }

    public Fecha(int day, int month, int year) {
        showCalendar();
        textField1.setText(String.valueOf(day));
        textField2.setText(String.valueOf(month));
        textField3.setText(String.valueOf(year));
    }

    public LocalDate getFecha() throws FechaException {
        String dia = textField1.getText();
        String mes = textField2.getText();
        String year = textField3.getText();
        try {
            int diaInt = Integer.parseInt(dia);
            int mesInt = Integer.parseInt(mes);
            int yearInt = Integer.parseInt(year);
            LocalDate.of(yearInt, mesInt, diaInt);
        } catch (NumberFormatException e) {
            throw new FechaException("Los valores introducidos no son numeros");
        } catch (DateTimeException e) {
            throw new FechaException("La fecha introducida no es válida");
        }
        if (Integer.parseInt(year) < 1900) {
            throw new FechaException("El año debe ser mayor que 1900");
        }
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(mes), Integer.parseInt(dia));
    }

    public void setFecha(LocalDate fecha) {
        textField1.setText(String.valueOf(fecha.getDayOfMonth()));
        textField2.setText(String.valueOf(fecha.getMonthValue()));
        textField3.setText(String.valueOf(fecha.getYear()));
    }
}
