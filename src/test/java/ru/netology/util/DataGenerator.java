package ru.netology.util;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import ru.netology.data.UserData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@UtilityClass
public class DataGenerator {
    Faker faker = new Faker(new Locale("ru"));
    Random random = new Random();
    List<String> enabledCities = new ArrayList<>();

    public UserData userGenerator(LocalDate earlyDate, int dateBound, String pattern) {
        return UserData.builder()
                .city(cityGenerator())
                .date(dateGenerator(earlyDate, dateBound, pattern))
                .name(nameGenerator())
                .phone(phoneGenerator())
                .build();
    }

    public String cityGenerator() {
        if (enabledCities.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                Selenide.open("https://moi-goroda.ru/collection/36/list");

                if (i < 0) {
                    $(By.id("link-to_next_page")).click();
                }
                enabledCities.addAll($$(".col h4").texts());
            }
            Selenide.closeWebDriver();
        }
        return enabledCities.get(random.nextInt(enabledCities.size()));
    }

    public String changeCity(String unsuitedCity) {
        String result;
        do {
            result = cityGenerator();
        } while (result.equals(unsuitedCity));
        return result;
    }

    public String dateGenerator(LocalDate earlyDate, int dateBound, String pattern) {
        return earlyDate
                .plusDays(random.nextInt(dateBound))
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    public String changeDate(LocalDate earlyDate, int dateBound, String pattern, String unsuitedDate) {
        String result;
        do {
            result = dateGenerator(earlyDate, dateBound, pattern);
        } while (result.equals(unsuitedDate));
        return result;
    }

    public String nameGenerator() {
        return faker.name().fullName();
    }

    public String changeName(String unsuitedName) {
        String result;
        do {
            result = nameGenerator();
        } while (result.equals(unsuitedName));
        return result;
    }

    public String phoneGenerator() {
        return faker.numerify("+7##########");
    }

    public String changePhone(String unsuitedPhone) {
        String result;
        do {
            result = phoneGenerator();
        } while (result.equals(unsuitedPhone));
        return result;
    }
}