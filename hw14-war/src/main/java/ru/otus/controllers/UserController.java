package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.model.Address;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DataLoadService;

import java.util.List;

@Controller
public class UserController {

    private final DBServiceUser usersService;

    public UserController(
            DBServiceUser usersService
            ,DataLoadService dataLoadService
    ) {
        this.usersService = usersService;

        dataLoadService.loadData();
    }

    @GetMapping({"/"})
    public String userListView(Model model) {
        List<User> users = usersService.getUserList();
        model.addAttribute("users", users);
        return "users.html";
    }

    @GetMapping("/create")
    public String userCreateView(Model model) {
        User user = new User();
        user.setAddress(new Address("", user));
        model.addAttribute("user", user);
        return "user.html";
    }

    @PostMapping("/save")
    public RedirectView userSave(@ModelAttribute User user) {
        usersService.saveUser(user);
        return new RedirectView("/", true);
    }
}
