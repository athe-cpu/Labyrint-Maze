package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class menuController {

    @RequestMapping("/")
    public String main() {
        return "LoginPage";
    }
    @RequestMapping("/MainMenu")
    public String menu() {
        return "menu";
    }
    @RequestMapping("/play")
    public String play() {
        return "difficulty";
    }
    @RequestMapping("/exit")
    public String exit() {
        return "redirect:/";
    }
    @RequestMapping("/about")
    public String about() {
        return "about";
    }
}
