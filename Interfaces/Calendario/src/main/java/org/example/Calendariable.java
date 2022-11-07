package org.example;

import java.time.LocalDate;

public interface Calendariable {

    LocalDate getFecha() throws FechaException;

    void setFecha(int day, int month, int year) throws FechaException;
}