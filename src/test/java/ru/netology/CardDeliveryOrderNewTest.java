package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.Data.DataGenerator;
import ru.netology.Data.RegistrationInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.Data.DataGenerator.generateDate;

public class CardDeliveryOrderNewTest {

    @Test
    void shouldSendRegistrationForm() {
        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");
        // отправка формы первоначально
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue(info.getCity());
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(3));
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue("+" + info.getPhone());
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button__text").shouldHave(Condition.text("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована на  " + generateDate(3)), Duration.ofSeconds(15));
        $("button.notification__closer").click();
        //System.out.println(info);

        // отправка формы с коррекцией даты
        $("[data-test-id='city'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), info.getCity());
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(5));
        $("[data-test-id='name'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), info.getName());
        $("[data-test-id='phone'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "+" + info.getPhone());
        $(".button__text").shouldHave(Condition.text("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));
        $$("button .button__text").find(Condition.text("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована на  " + generateDate(5)), Duration.ofSeconds(15));
    }
}
