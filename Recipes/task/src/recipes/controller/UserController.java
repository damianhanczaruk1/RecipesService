package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.service.UserService;
import recipes.model.User;

import javax.validation.Valid;


@RestController
public class UserController {

    @Autowired
    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public void postUser(@Valid @RequestBody User user){
        userService.addUser(user);
    }

}
