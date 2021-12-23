package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.Data.DataGenerator;
import ru.netology.Data.RegistrationInfo;

import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderNewTest {

    @Test
    void shouldSendRegistrationForm() {
        //open("http://localhost:9999/");
        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");
        System.out.println(info);
    }
}
