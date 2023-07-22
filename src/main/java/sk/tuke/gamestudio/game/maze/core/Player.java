package sk.tuke.gamestudio.game.maze.core;

import java.util.Scanner;

public class Player {
    private  int playerX;
    private  int playerY;
    private  final Field tiles;
    private  int previousRight;
    private  int previousDown;
    private  int previousTile;
    private  int previousTreasureTile ;
    Scanner scanner = new Scanner(System.in);
    public Player(Field  tiles){
        this.tiles=tiles;
        this.playerX = setPlayerX();
        this.playerY = setPlayerY();
        placePlayer();
    }
    public Player(Field  tiles, int playerX,int playerY){
        this.tiles=tiles;
        this.playerX = playerX;
        this.playerY = playerY;
        placePlayer();
    }
    private int setPlayerX(){
        System.out.println("Enter player X location.");
        playerX = scanner.nextInt();
        if(playerX>tiles.getRowCounts()-1|playerX<0){
            System.out.println("Number is out of the game field.");
            setPlayerX();
        }
        return playerX;
    }
    private int setPlayerY(){
        System.out.println("Enter player Y location.");
        playerY = scanner.nextInt();
        if(playerY>tiles.getColumnCounts()-1|playerY<0){
           System.out.println("Number is out of the game field.");
           setPlayerY();
        }
        return playerY;
    }
    private void placePlayer(){
        previousTreasureTile = tiles.getPreviousTreasureTile();
        previousTile = tiles.getTiles(playerX,playerY);
        tiles.setPreviousTile(tiles.getTiles(playerX,playerY));
        checkNextRight();
        checkNextDown();
        tiles.setTiles(playerX,playerY,64);
    }

    public void moveRight(){
        if((previousRight & 8) == 0){
            System.out.println("You cant move this way!");
            return;
        }
        tiles.setTiles(playerX,playerY,previousTile);
        playerX++;
        previousTile=tiles.getTiles(playerX,playerY);
        checkNextRight();
        checkNextDown();
        tiles.setTiles(playerX,playerY,64);
        tiles.setPreviousTile(previousTile);
    }
    public void moveLeft(){
        if((previousTile & 8) == 0){
            System.out.println("You cant move this way!");
            return;
        }
        tiles.setTiles(playerX,playerY,previousTile);
        playerX--;
        previousTile=tiles.getTiles(playerX,playerY);
        checkNextRight();
        checkNextDown();
        tiles.setTiles(playerX,playerY,64);
        tiles.setPreviousTile(previousTile);
    }
    public void moveDown(){
        if(previousDown!=88&&(previousDown & 1) == 0){
            System.out.println("You cant move this way!");
            return;
        }
        tiles.setTiles(playerX,playerY,previousTile);
        playerY++;
        previousTile=tiles.getTiles(playerX,playerY);
        checkNextRight();
        checkNextDown();
        tiles.setTiles(playerX,playerY,64);
        tiles.setPreviousTile(previousTile);
    }
    public void moveUp(){
        if((previousTile & 1) == 0&&previousTile!=88){
            System.out.println("You cant move this way!");
            return;
        }
        tiles.setTiles(playerX,playerY,previousTile);
        playerY--;
        previousTile=tiles.getTiles(playerX,playerY);
        checkNextRight();
        checkNextDown();
        tiles.setTiles(playerX,playerY,64);
        tiles.setPreviousTile(previousTile);
    }
    private void checkNextRight(){
        if(playerX<tiles.getRowCounts()-1) {
            previousRight = tiles.getTiles(playerX + 1,playerY);
        }else {
            previousRight = tiles.getTiles(0,playerY);
        }
        if(previousRight==88){
            previousRight = previousTreasureTile;
        }
    }
    private void checkNextDown(){
        if(playerY<tiles.getColumnCounts()-1) {
            previousDown = tiles.getTiles(playerX,playerY + 1);
        }else {
            previousDown = tiles.getTiles(playerX,0);
        }
        if(previousDown==88){
            previousDown = previousTreasureTile;
        }
    }
}
