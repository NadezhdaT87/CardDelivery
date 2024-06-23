package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.chord;

public class CardDeliveryTest {
    private String generateData(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void testSubmittingFormPositiveData() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city']input").setValue("Рязань");
        String planningData = generateData(4, "dd.MM.yyyy");
        $("[data-test-id='date']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date']").setValue(planningData);
        $("[data-test-id='name']").setValue("Надежда Тарасова");
        $("[data-test-id='phone']").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на" + planningData));
    }
}
