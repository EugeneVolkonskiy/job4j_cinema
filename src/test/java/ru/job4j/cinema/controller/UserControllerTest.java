package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private HttpServletRequest httpServletRequest;
    private HttpSession session;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        httpServletRequest = new MockHttpServletRequest();
        session = new MockHttpSession();
        userController = new UserController(userService);
    }

    @Test
    public void whenRequestRegistrationPageThenGetRegistrationPage() {
        assertThat(userController.getRegistrationPage()).isEqualTo("users/register");
    }

    @Test
    public void whenNotRegisteredThenGetErrorMessage() {
        var user = new User(1, "Ivan", "test@mail.ru", "test");
        when(userService.save(any())).thenReturn(Optional.empty());
        var expectedMessage = String.format("Пользователь с почтой %s уже существует", user.getEmail());

        var model = new ConcurrentModel();
        var view = userController.register(user, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("users/register");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenRegisteredSuccessfullyThenRedirectToFilmsListPage() {
        var user = new User(1, "Ivan", "test@mail.ru", "test");
        when(userService.save(user)).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(user, model);

        assertThat(view).isEqualTo("redirect:/films/list");
    }

    @Test
    public void whenRequestLoginPageThenGetLoginPage() {
        assertThat(userController.getLoginPage()).isEqualTo("users/login");
    }

    @Test
    public void whenLoginUserFailedThenGetErrorMessage() {
        var user = new User(1, "Ivan", "test@mail.ru", "test");
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.empty());
        var expectedMessage = "Почта или пароль введены неверно";

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, httpServletRequest);
        var actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenLoginSuccessfullyThenRedirectFilmsListPage() {
        var user = new User(1, "Ivan", "test@mail.ru", "test");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, httpServletRequest);

        assertThat(view).isEqualTo("redirect:/films/list");
    }

    @Test
    public void whenLogoutRequestThenRedirectToLoginPage() {
        assertThat(userController.logout(session)).isEqualTo("redirect:/users/login");
    }
}