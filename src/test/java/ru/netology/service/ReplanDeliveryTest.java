package ru.netology.service;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.UserData;
import ru.netology.util.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class ReplanDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void shouldReplanDeliveryDate() {
        UserData user = DataGenerator.userGenerator();

        Selenide.open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue(user.getCity());
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(user.getDate());

        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());

        if (!$("[data-test-id='agreement'] input").isSelected()) {
            $("[data-test-id='agreement']").click();
        }
        $(".grid-row .button").click();

        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.allOf(
                        Condition.visible,
                        Condition.exactText("Встреча успешно запланирована на " + user.getDate())
                ), Duration.ofSeconds(15));

        user = user.withDate(DataGenerator.changeDate(user.getDate()));

        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(user.getDate());

        $(".grid-row .button").click();

        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(Condition.allOf(
                        Condition.visible,
                        Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать?\n\nПерепланировать")
                ), Duration.ofSeconds(15));

        $("[data-test-id='replan-notification'] .button").click();

        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.allOf(
                        Condition.visible,
                        Condition.exactText("Встреча успешно запланирована на " + user.getDate())
                ), Duration.ofSeconds(15));
    }
}