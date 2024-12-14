package ru.netology.auth.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.auth.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BankLoginTest {
    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");

    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("h2").should(Condition.visible, Duration.ofSeconds(10))
                .should(Condition.exactText("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfUserNotRegistered() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");

        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").should(Condition.visible, Duration.ofSeconds(10))
                .should(Condition.text("Неверно указан логин или пароль"));
    }
    @Test
    @DisplayName("Should get error message if registered user is blocked")
    void shouldGetErrorIfRegisteredUserBlocked() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("blocked");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").should(Condition.visible, Duration.ofSeconds(10))
                .should(Condition.text("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if entered with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(DataGenerator.generateLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").should(Condition.visible, Duration.ofSeconds(10))
                .should(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if entered with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.generatePassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").should(Condition.visible, Duration.ofSeconds(10))
                .should(Condition.text("Неверно указан логин или пароль"));
    }
}

