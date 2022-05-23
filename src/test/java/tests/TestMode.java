package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataGenerator.Registration.getRegisteredUser;

public class TestMode {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }
    @Test
    void shouldActiveUserValidData() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldBe(visible).shouldBe(text("Личный кабинет"));
    }

    @Test
    void shouldBlockedUserValidData() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldBe(visible).shouldBe(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldActiveUserInvalidLogin() {
        var invalidData = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(DataGenerator.getRandomLogin());
        $("[data-test-id='password'] input").setValue(invalidData.getPassword());
        $("[data-test-id='action-login'] .button__content ").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(text("Ошибка"));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldActiveUserInvalidPassword() {
        var invalidData = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(invalidData.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.getRandomPassword());
        $("[data-test-id='action-login'] .button__content ").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(text("Ошибка"));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldBlockedUserInvalidPassword() {
        var invalidData = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(invalidData.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.getRandomPassword());
        $("[data-test-id='action-login'] .button__content ").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(text("Ошибка"));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldBlockedUserInvalidLogin() {
        var invalidData = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(DataGenerator.getRandomLogin());
        $("[data-test-id='password'] input").setValue(invalidData.getPassword());
        $("[data-test-id='action-login'] .button__content ").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(text("Ошибка"));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

}
