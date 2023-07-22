package sk.tuke.gamestudio.game.maze.ConsoleUI;

import lombok.Data;
import sk.tuke.gamestudio.game.maze.Regex;
import sk.tuke.gamestudio.entity.RatingAndComments;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.maze.core.*;
import sk.tuke.gamestudio.service.RatingAndCommentsService;
import sk.tuke.gamestudio.service.ScoreService;

import java.awt.event.KeyEvent;
import java.util.*;

    @Data
    public class ConsoleUI {
    private final Regex regex;

    private ScoreService scoreService;
    private RatingAndCommentsService ratingAndCommentsService;

    private PlayerMove playerMove;
    private GameState gameState ;
    private Field field;
    private Treasure treasure;
    private Player player;
    private Timer timer;
    private TimerTask task ;
    private int levelPassed ;
    private boolean invisibleMode = false;
    private int secondsPassed;
    private int easyTime;
    private int mediumTime;
    private int hardTime;
    private String playerName;
    private Boolean alreadyRated;
    private Random random = new Random();
    private int wasd1;

    public ConsoleUI(ScoreService scoreService,RatingAndCommentsService ratingAndCommentsService){
        this.scoreService = scoreService;
        this.ratingAndCommentsService = ratingAndCommentsService;
        alreadyRated = false;
        secondsPassed = 0;
        levelPassed = 0;
        regex = new Regex();
        timer = new Timer();
        askPlayerName();
        start();
    }
    private int a =0;
    private void start() {
        gameState = GameState.PLAYING;
        printTopScores();
        welcome();
        wasd1 = regex.menuInput();
        if(wasd1==-1){
            start();
        }
        chooseTape(wasd1);
        if(a==0) {
            resetTime();
            a=1;
        }
    }
    private void askPlayerName(){
        System.out.println("Enter your name.");
        Scanner scanner = new Scanner(System.in);
        playerName = scanner.next().toLowerCase();
    }
    private void welcome() {
        System.out.println("Welcome to Maze game.");
        System.out.println("Choose a game tape.");
        System.out.println("1 - Easy difficulty.");
        System.out.println("2 - Medium difficulty.");
        System.out.println("3 - Hard difficulty.");
        System.out.println("4 - Your settings.");
        System.out.println("5 - Change to invisible maze.");
        System.out.println("6 - Exit.");
        System.out.println("7 - Rate and Comment game.");
        System.out.println("8 - Show rate and comments.");
        System.out.println("0 - About game.");
    }
    private void resetTime() {
        this.timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                secondsPassed++;
            }
        };
        this.timer.schedule(task,1000,1000);
    }

    private void chooseTape(int wasd){
        int randomX;
        int randomY;
        if(wasd==1){
            randomX = random.nextInt(4,7);
            randomY = random.nextInt(4,7);
            field = new Field(randomX,randomY);
            treasure = new Treasure(field,randomX-1,randomY-1);
            player = new Player(field,0,0);
            easyTime = 1;
        }else if (wasd==2){
            randomX = random.nextInt(8,14);
            randomY = random.nextInt(8,14);
            field = new Field(randomX,randomY);
            treasure = new Treasure(field,randomX-1,randomY-1);
            player = new Player(field,0,0);
            mediumTime = 1;
        }else if (wasd==3){
            randomX = random.nextInt(16,28);
            randomY = random.nextInt(16,28);
            field = new Field(randomX,randomY);
            treasure = new Treasure(field,randomX-1,randomY-1);
            player = new Player(field,0,0);
            hardTime = 1;
        }else if (wasd==4){
            field = new Field();
            treasure = new Treasure(field);
            player = new Player(field);
        }else if (wasd==5){
            if(!invisibleMode){
                invisibleMode = true;
                System.out.println("Invisible mode is on");
            }else {
                invisibleMode = false;
                System.out.println("Invisible mode is off");
            }
            start();
        }else if (wasd==6){
            System.out.println("See you later.");
            System.exit(0);
        }else if (wasd==7){
            if (alreadyRated){
                System.out.println("You already rated this game.");
            }
            System.out.println("Do you want rate game(y/n).");
            Scanner scanner = new Scanner(System.in);
            int rate;
            char yn1 = scanner.next().charAt(0);
            if(yn1=='y') {
                System.out.println("Enter your game rate (0-5).");
                do {
                    rate = scanner.nextInt();
                } while (rate > 5 | rate < 0);
                    System.out.println("Enter your comment.(dont use space)");
                    String comment;

                    comment = scanner.next().toLowerCase();

                ratingAndCommentsService.addRC(new RatingAndComments(playerName, "maze", rate, comment));

                alreadyRated = true;
                start();
            }else if (yn1=='n'){
                start();
            }else{
                chooseTape(7);
            }
        }else if (wasd==8) {
                System.out.println("--------------------------------------------");
                List<RatingAndComments> ratingAndComments = ratingAndCommentsService.getTopScores("maze");
                int i=1;
                for (RatingAndComments ratingAndComment : ratingAndComments) {
                    System.out.printf("%d, %s,Rate %d, Comment %s\n",i, ratingAndComment.getPlayer(), ratingAndComment.getRate(), ratingAndComment.getComment());
                    i++;
                }
           /* List<RatingAndComments> rate = ratingAndCommentsService.getAverage("maze");
            float avr = 0;
        int i1 = 0;
        for (RatingAndComments ratingAndComment : rate) {
            avr = avr + ratingAndComment.getRate();
            i1++;
        }
        avr = avr/i1;*/
            System.out.println("Average score - " + ratingAndCommentsService.getAverage("maze"));

                System.out.println("--------------------------------------------");
            start();
        }else if (wasd==0){
            System.out.println("""
                    Classic labyrinth, the goal is to get from point A to point B by moving the arrows.\s
                    A point it is player, B point it is treasure.
                    The labyrinth version is for one player.
                    To win chose a difficulty and passed three levels.
                    Your settings mode wont affect on records table.
                    Difficulty levels change rows and columns number with early defined player and treasure position.
                    In your setting you can define player position, rows and columns number but not treasure.
                    Rows and columns number cant be more than 50.""");
            start();
        }
    }
    private void processInput(){

        if(treasure.found()){
            gameState = GameState.SOLVED;
            return;
        }

        Scanner scanner = new Scanner(System.in);

        char wasd;
        wasd = scanner.next().charAt(0);
        if (wasd=='x'|wasd=='X'){
            gameState = GameState.EXIT;
            return;
        }
        if(wasd=='d'|wasd=='D'| wasd== KeyEvent.VK_RIGHT){
            playerMove = PlayerMove.RIGHT;
            playerNextState();
        }else if (wasd =='s'| wasd =='S'|wasd == KeyEvent.VK_DOWN){
            playerMove = PlayerMove.DOWN;
            playerNextState();
        }else if (wasd =='a'| wasd =='A'|wasd == KeyEvent.VK_LEFT){
            playerMove = PlayerMove.LEFT;
            playerNextState();
        }else if(wasd =='w'| wasd =='W'|wasd == KeyEvent.VK_UP){
            playerMove = PlayerMove.UP;
            playerNextState();
        }else {
            System.out.println("Incorrect input.");
            System.out.println("Try again.");

            processInput();
        }

        if(treasure.found()){
            gameState = GameState.SOLVED;
        }
    }

    public void play() {
        do {
            if(invisibleMode) {
                field.displayInvisible();
            }else
                field.display();
            System.out.println("Move with [wasd], exit with x");
            System.out.println("Level passed - "+levelPassed);
            processInput();
        } while (gameState==GameState.PLAYING);

        field.display();

        if (gameState == GameState.EXIT) {
            endGameChoose();
        } else if (gameState == GameState.SOLVED){
            if(levelPassed==2){
                addScore();

                System.out.println("YOU WIN!!!");
                levelPassed++;
                System.out.println("Level passed - "+levelPassed);
                System.out.println("Your time(in seconds) - "+secondsPassed);
                endGameChoose();
            }else{
                levelPassed++;
                chooseTape(wasd1);
                gameState = GameState.PLAYING;
                play();
            }
        }
    }
    private void addScore(){
        if(invisibleMode) {
            if (easyTime == 1) {
                scoreService.addScore(new Score(playerName, "maze", levelPassed + 1, new Date(), invisibleMode, secondsPassed, 0, 0, (levelPassed + 1) * 100 - secondsPassed));
            } else if (mediumTime == 1) {
                scoreService.addScore(new Score(playerName, "maze", levelPassed + 1, new Date(), invisibleMode, 0, secondsPassed, 0,((levelPassed + 1) * 100)*2 - secondsPassed));
            } else if (hardTime == 1) {
                scoreService.addScore(new Score(playerName, "maze", levelPassed + 1, new Date(), invisibleMode, 0, 0, secondsPassed,((levelPassed + 1) * 100)*3 - secondsPassed));
            }
        }else {
            if (easyTime == 1) {
                scoreService.addScore(new Score(playerName, "maze", levelPassed + 1, new Date(), invisibleMode, secondsPassed, 0, 0, (levelPassed + 1)*80 - secondsPassed));
            } else if (mediumTime == 1) {
                scoreService.addScore(new Score(playerName, "maze", levelPassed + 1, new Date(), invisibleMode, 0, secondsPassed, 0,(levelPassed + 1)*100 - secondsPassed));
            } else if (hardTime == 1) {
                scoreService.addScore(new Score(playerName, "maze", levelPassed + 1, new Date(), invisibleMode, 0, 0, secondsPassed,(levelPassed + 1)*100 - secondsPassed));
            }
        }
    }
    private void endGameChoose(){
        easyTime = 0;
        mediumTime = 0;
        hardTime = 0;
        System.out.println("Would you like exit or return to menu(y-exit,n-menu)");
        Scanner scanner = new Scanner(System.in);
        char wasd = scanner.next().charAt(0);
        if(wasd=='y'){
            System.out.println("See you later.");
            timer.cancel();
            timer.purge();
            System.exit(0);
        }else if (wasd=='n'){
            this.timer.cancel();
            this.timer.purge();
            secondsPassed = 0;
            levelPassed = 0;
            a=0;
            start();
            play();
        }else{
            endGameChoose();
        }
    }
    private void printTopScores() {
        System.out.println("--------------------------------------------");
        System.out.println("Top scores.");

        List<Score> scores = scoreService.getTopScores("maze");
        int i=1;
        for (Score score : scores) {
            System.out.printf("%d, %s, Level passed %d, In invisible mode %b, Time easy mode %d, Time medium mode %d,Time hard mode %d, Overall score %d\n",i, score.getPlayer(), score.getLevelPassed(), score.isInvisible(),score.getEasyDifficultyTime(),score.getMediumDifficultyTime(),score.getHardDifficultyTime(),score.getScore() );
            i++;
        }
        System.out.println("--------------------------------------------");
    }

    private void playerNextState(){
        switch (playerMove) {
            case RIGHT -> player.moveRight();
            case LEFT -> player.moveLeft();
            case DOWN -> player.moveDown();
            case UP -> player.moveUp();
            default -> throw new IllegalStateException("Unexpected value: " + playerMove);
        }
    }
}
