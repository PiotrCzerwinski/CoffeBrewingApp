package com.example.brewing.ui;

import com.example.brewing.model.User;
import com.example.brewing.repositories.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Route("login-view")
@UIScope
@Theme(variant = Lumo.DARK)
public class LoginView extends VerticalLayout {
    @Autowired
    UserRepository userRepository;
    private HorizontalLayout buttonsHL = new HorizontalLayout();
    private TextField loginTF = new TextField("Login");
    private PasswordField passwordPF = new PasswordField("Password");
    private Button loginButton = new Button("Log in");
    private Button registerButton = new Button("Register");
    @PostConstruct
    public void init(){
        setAlignItems(Alignment.CENTER);
        loginButton.addClickListener(buttonClickEvent -> {
            if(!loginTF.isEmpty() && !passwordPF.isEmpty()) {
                Optional<User> optionalUser = userRepository.findByLoginAndPassword(loginTF.getValue(),passwordPF.getValue());
                if(optionalUser.isPresent()) {
                    UI.getCurrent().getSession().setAttribute("user",optionalUser.get());
                    UI.getCurrent().navigate("user-page");
                    loginTF.clear();
                    passwordPF.clear();
                } else {
                    Notification.show("There is no such user");
                }
            } else {
                Notification.show("Fields cannot be empty");
            }
        });

        registerButton.addClickListener(click ->{
            UI.getCurrent().navigate("registration-form");
        });

        buttonsHL.add(loginButton,registerButton);
        add(loginTF,passwordPF,buttonsHL);
    }
}