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
import java.util.ArrayList;

@Route("registration-form")
@UIScope
@Theme(variant = Lumo.DARK)
public class RegistrationForm extends VerticalLayout {
    @Autowired
    UserRepository userRepository;

    private HorizontalLayout buttonsHL = new HorizontalLayout();
    private TextField loginTF = new TextField("Login");
    private PasswordField passwordPF = new PasswordField("Password");
    private PasswordField repeatedPasswordPF = new PasswordField("Repeat password");
    private Button registerButton = new Button("Register");
    private Button backButton = new Button("Back");
    @PostConstruct
    public void init(){
        setAlignItems(Alignment.CENTER);
        registerButton.addClickListener(buttonClickEvent -> {
            if(userRepository.findByLogin(loginTF.getValue()).isEmpty()) {
                if(passwordPF.getValue().equals(repeatedPasswordPF.getValue())) {
                    User user = new User(loginTF.getValue(), passwordPF.getValue(),
                            new ArrayList<>(), new ArrayList<>(),
                            new ArrayList<>(), new ArrayList<>());
                    userRepository.save(user);
                    UI.getCurrent().navigate("login-view");
                    }else {
                    Notification.show("Passwords dont match");
                    }
            } else {
                Notification.show("Login taken");
            }

        });

        backButton.addClickListener(click ->{
            UI.getCurrent().navigate("login-view");
        });

        buttonsHL.add(registerButton,backButton);
        add(loginTF,passwordPF,repeatedPasswordPF,buttonsHL);
    }
}
