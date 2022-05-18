package case_in.db.demo.controllers;


import case_in.db.demo.entity.User;
import case_in.db.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "/registration";
    }

    @PostMapping("/registration")
    public String addUser(User userForm, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            System.out.println("ERR: binding");
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            System.out.println("ERR: Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            System.out.println("ERR: Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
}
