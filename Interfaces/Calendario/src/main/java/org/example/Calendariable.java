package org.example;

import java.time.LocalDate;

public interface Calendariable {
    void showCalendar();

    LocalDate getFecha() throws FechaException;

    void setFecha(LocalDate fecha);
}
