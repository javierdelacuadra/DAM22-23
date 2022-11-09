package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDate;

public class Fecha extends JPanel implements Calendariable {

    JTextField textField1 = new JTextField(2);
    JTextField textField2 = new JTextField(2);
    JTextField textField3 = new JTextField(4);
    JLabel label1 = new JLabel("Día");
    JLabel label2 = new JLabel("Mes");
    JLabel label3 = new JLabel("Año");

    public Fecha() {
        setSize(300, 300);
        setLayout(new GridLayout(3, 2, 0, 5));
        add(label1);
        add(textField1);
        add(label2);
        add(textField2);
        add(label3);
        add(textField3);
        label1.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);
    }

    public Fecha(int day, int month, int year) throws FechaException {
        this();
        setFecha(day, month, year);
    }

    public LocalDate getFecha() throws FechaException {
        String dia = textField1.getText();
        String mes = textField2.getText();
        String year = textField3.getText();
        if (dia.isEmpty() || mes.isEmpty() || year.isEmpty()) {
            throw new FechaException("No se puede dejar ningún campo vacío");
        }
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

    public void setFecha(int day, int month, int year) throws FechaException {
        if (year < 1900) {
            throw new FechaException("El año debe ser mayor que 1900");
        }
        try {
            LocalDate.of(year, month, day);
            textField1.setText(String.valueOf(day));
            textField2.setText(String.valueOf(month));
            textField3.setText(String.valueOf(year));
        } catch (DateTimeException e) {
            throw new FechaException("La fecha introducida no es válida");
        }
    }
}
