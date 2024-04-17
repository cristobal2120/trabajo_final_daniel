package com.example.proyect;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class DateUnitTest {

    @Test
    public void getCurrentDate_isCorrect() {
        // Configurar el formato esperado
        SimpleDateFormat expectedFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Obtener la fecha actual utilizando la función
        String actualDate = date.getCurrentDate();

        // Obtener la fecha actual utilizando el formato esperado
        String expectedDate = expectedFormat.format(Calendar.getInstance().getTime());

        // Comparar las fechas obtenidas
        assertEquals(expectedDate, actualDate);
    }
}