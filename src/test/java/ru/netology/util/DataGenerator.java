package ru.netology.util;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import ru.netology.data.UserData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@UtilityClass
public class DataGenerator {
    Faker faker = new Faker(new Locale("ru"));
    Random random = new Random();

    List<String> enabledCities = new ArrayList<>(List.of(
            "Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Белгород", "Биробиджан", "Благовещенск",
            "Брянск", "Великий Новгород", "Владивосток", "Владикавказ", "Владимир", "Волгоград", "Вологда", "Воронеж",
            "Гатчина", "Геническ", "Горно-Алтайск", "Грозный", "Донецк", "Екатеринбург", "Иваново", "Ижевск", "Иркутск",
            "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Красногорск", "Краснодар",
            "Красноярск", "Курган", "Курск", "Кызыл", "Липецк", "Луганск", "Магадан", "Магас", "Майкоп", "Махачкала",
            "Мелитополь", "Москва", "Мурманск", "Нальчик", "Нарьян-Мар", "Нижний Новгород", "Новосибирск", "Омск", "Орёл",
            "Оренбург", "Пенза", "Пермь", "Петрозаводск", "Петропавловск-Камчатский", "Псков", "Ростов-на-Дону", "Рязань",
            "Салехард", "Самара", "Санкт-Петербург", "Саранск", "Саратов", "Севастополь", "Симферополь", "Смоленск",
            "Ставрополь", "Сыктывкар", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Улан-Удэ", "Ульяновск", "Уфа", "Хабаровск",
            "Ханты-Мансийск", "Чебоксары", "Челябинск", "Черкесск", "Чита", "Элиста", "Южно-Сахалинск", "Якутск", "Ярославль"
            ));

    LocalDate earlyDate = LocalDate.now().plusDays(3);
    int dateBound = 14;
    String pattern = "dd.MM.yyyy";


    public UserData userGenerator() {
        return new UserData(
                cityGenerator(),
                dateGenerator(),
                nameGenerator(),
                phoneGenerator()
        );
    }

    public String cityGenerator() {
        return enabledCities.get(random.nextInt(enabledCities.size()));
    }

    public String changeCity(String unsuitedCity) {
        String result = cityGenerator();

        for (int i = 0; i < 3; i++) {
            if (result.equals(unsuitedCity)) {
                result = cityGenerator();
            } else break;
        }
        return result;
    }

    public String dateGenerator() {
        return earlyDate
                .plusDays(random.nextInt(dateBound))
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    public String changeDate(String unsuitedDate) {
        String result = dateGenerator();

        for (int i = 0; i < 3; i++) {
            if (result.equals(unsuitedDate)) {
                result = dateGenerator();
            } else break;
        }
        return result;
    }

    public String nameGenerator() {
        return faker.name().fullName();
    }

    public String changeName(String unsuitedName) {
        String result = nameGenerator();

        for (int i = 0; i < 3; i++) {
            if (result.equals(unsuitedName)) {
                result = nameGenerator();
            } else break;
        }
        return result;
    }

    public String phoneGenerator() {
        return faker.numerify("+7##########");
    }

    public String changePhone(String unsuitedPhone) {
        String result = phoneGenerator();

        for (int i = 0; i < 3; i++) {
            if (result.equals(unsuitedPhone)) {
                result = phoneGenerator();
            } else break;
        }
        return result;
    }
}