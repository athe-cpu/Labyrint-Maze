package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.RandC;
import sk.tuke.gamestudio.entity.RatingAndComments;
import sk.tuke.gamestudio.entity.RegistrationLoginEntity;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.RatingAndCommentsService;
import sk.tuke.gamestudio.service.RegisterService;

import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    private User loggedUser;
    private String alreadyInUse;
    private Boolean already =false;
    private RandC randC;
    @Autowired
    private RatingAndCommentsService ratingAndCommentsService;
    @Autowired
    private RegisterService registerService;
    @RequestMapping("/logPanel")
    public String index() {
        return "LoginPage";
    }

    public String getAlreadyInUse(){
        return alreadyInUse;
    }
    public boolean getAlready(){
        return already;
    }
    @RequestMapping("/register")
    public String register(String register, String password) {
        if (loggedUser!=null) {
            loggedUser =null;
        }
        if(loggedUser==null) {
            List<RegistrationLoginEntity>  allUsers = registerService.getUsers("maze");
            if (allUsers!=null) {
                for (RegistrationLoginEntity d : allUsers) {
                    if ((register.equals(d.getPlayer()))) {
                        alreadyInUse = "       Already in use";
                        already = true;
                        return "redirect:/logPanel";
                    }
                }
            }
            registerService.addUser(new RegistrationLoginEntity(register,password,"maze"));
            loginUser(register,password);
            already = false;
        }


        return "redirect:/MainMenu";
    }
    public void loginUser(String login, String password) {
        if(loggedUser==null) {
            List<RegistrationLoginEntity>  allUsers = registerService.getUsers("maze");
            if(allUsers!=null) {
                for (RegistrationLoginEntity d : allUsers) {
                    if ((login.equals(d.getPlayer())) && (password.equals(d.getPassword()))) {
                        loggedUser = new User(login);
                        break;
                    }
                }
            }
        }
    }
    @RequestMapping("/login")
    public String login(String login, String password) {
        if(loggedUser!=null) {
            loggedUser=null;
        }
        if(loggedUser==null) {
            List<RegistrationLoginEntity>  allUsers = registerService.getUsers("maze");
            if(allUsers!=null) {
                for (RegistrationLoginEntity d : allUsers) {
                    if ((login.equals(d.getPlayer())) && (password.equals(d.getPassword()))) {
                        loggedUser = new User(login);
                        already = false;
                        return "redirect:/MainMenu";
                    }
                }
                already = true;
                alreadyInUse = "Incorrect name or password";
                return "redirect:/logPanel";
            }
        }

        return "redirect:/MainMenu";
    }
    @RequestMapping( "/comment")
    public String showPage( String comment, Integer rate) {
        if(comment == null){
            comment = " ";
        }
        if(rate == null||rate<0||rate>5){
            rate = 0;
        }
        randC = new RandC(comment,rate);
        boolean isPlayer = true;
        if(loggedUser!=null) {
           List<RatingAndComments> allTable = ratingAndCommentsService.getTopScores("maze");
           for (RatingAndComments d : allTable){
               if(loggedUser.getLogin().equals(d.getPlayer())){
                   isPlayer = false;
                   ratingAndCommentsService.updateByName(d.getIdent());
                   ratingAndCommentsService.addRC(new RatingAndComments(loggedUser.getLogin(), "maze", randC.getRate(), randC.getComment()));
                   break;
               }
           }
           if(isPlayer) {
               ratingAndCommentsService.addRC(new RatingAndComments(loggedUser.getLogin(), "maze", randC.getRate(), randC.getComment()));
           }
           }
        return "redirect:/maze";
    }
    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/maze/menu";
    }

    public RandC getRatAndCom() {
        return randC;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

}
