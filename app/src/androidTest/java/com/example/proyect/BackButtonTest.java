package com.example.proyect;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.proyect.MainActivity;
import com.example.proyect.Mainlogin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BackButtonTest {

    private ActivityScenario<Mainlogin> activityScenario;

    @Before
    public void setUp() {
        activityScenario = ActivityScenario.launch(Mainlogin.class);
    }

    @After
    public void tearDown() {
        activityScenario.close();
    }

    @Test
    public void testBackButton() {
        // Realiza acciones en la actividad actual si es necesario
        // Por ejemplo, puede interactuar con vistas, escribir texto, hacer clic en botones, etc.

        // Presiona el botón de "volver"
        Espresso.onView(ViewMatchers.withId(R.id.back)).perform(ViewActions.click());

        // Verifica que la actividad MainActivity se haya iniciado
        ActivityScenario<MainActivity> mainActivityScenario = ActivityScenario.launch(MainActivity.class);

        // Puedes agregar más verificaciones según sea necesario para asegurarte de que la navegación sea correcta.
        // Por ejemplo, verificar que ciertas vistas estén presentes en MainActivity.
    }
}
