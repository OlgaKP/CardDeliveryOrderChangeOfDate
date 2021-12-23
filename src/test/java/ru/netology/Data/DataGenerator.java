package ru.netology.Data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Registration {
        public static RegistrationInfo generateInfo (String locale){
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationInfo(
                    faker.address().cityName(),
                    generateDate(4),
                    faker.name().name(),
                    faker.phoneNumber().phoneNumber());
        }
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
