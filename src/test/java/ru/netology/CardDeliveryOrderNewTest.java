package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.Data.DataGenerator;
import ru.netology.Data.RegistrationInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.Data.DataGenerator.generateDate;

public class CardDeliveryOrderNewTest {

    RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");
    String strPhoneNumber = String.valueOf(Long.parseLong((info.getPhone()).replaceAll("\\D+", "")));
    String planningDate = generateDate(5);

    @Test
    void shouldSendRegistrationForm() {
        // отправка формы первоначально
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue(info.getCity());
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), info.getDate());
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue("+" + strPhoneNumber);
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + info.getDate()));
        $("button.notification__closer").click();
        System.out.println(info);

        // отправка формы с коррекцией даты
        $("[data-test-id='city'] input")
                .setValue(Keys.chord(Keys.CONTROL, "a", "x", "v"));
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input")
                .setValue(Keys.chord(Keys.CONTROL, "a", "x", "v"));
        $("[data-test-id='phone'] input")
                .setValue(Keys.chord(Keys.CONTROL, "a", "x", "v"));
        $(".button").click();
    }
}
