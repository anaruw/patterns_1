package ru.netology.service;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.netology.data.UserData;
import ru.netology.util.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.$;

public class ReplanDeliveryTest {
    LocalDate earlyDate = LocalDate.now().plusDays(3);
    int dateBound = 14;
    String pattern = "dd.MM.yyyy";
    UserData testUser = DataGenerator.userGenerator(earlyDate, dateBound, pattern);

    @Test
    public void shouldReplanDeliveryDate() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue(testUser.getCity());
        $("[data-test-id='date'] input")
                .doubleClick()
                .setValue(testUser.getDate());

        $("[data-test-id='name'] input").setValue(testUser.getName());
        $("[data-test-id='phone'] input").setValue(testUser.getPhone());

        if (!$("[data-test-id='agreement'] input").isSelected()) {
            $("[data-test-id='agreement']").click();
        }
        $(".grid-row .button").click();

        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .innerHtml().equals("Встреча успешно запланирована на " + testUser.getDate());

        testUser = testUser.withDate(DataGenerator.changeDate(earlyDate, dateBound, pattern, testUser.getDate()));

        $("[data-test-id='date'] input")
                .doubleClick()
                .setValue(testUser.getDate());

        $(".grid-row .button").click();

        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .innerHtml().equals("У вас уже запланирована встреча на другую дату. Перепланировать?");

        $("[data-test-id='replan-notification'] .button").click();

        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .innerHtml().equals("Встреча успешно запланирована на " + testUser.getDate());

        Selenide.closeWebDriver();
    }
}