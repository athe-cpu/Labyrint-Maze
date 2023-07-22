package sk.tuke.gamestudio.game.maze.core;

import lombok.Data;

import java.util.Random;

@Data
public class Treasure {
    private final int TreasureX ;
    private final int TreasureY ;
    private final Field tiles;
    Random random = new Random();
    public Treasure(Field  tiles){
        this.TreasureX = random.nextInt(0,tiles.getRowCounts());
        this.TreasureY = random.nextInt(0,tiles.getColumnCounts());
        this.tiles=tiles;
        placeTreasure();
    }
    public Treasure(Field  tiles,int TreasureX,int TreasureY){
        this.TreasureX = TreasureX;
        this.TreasureY = TreasureY;
        this.tiles=tiles;
        placeTreasure();
    }
    private void placeTreasure(){
        tiles.setPreviousTreasureTile(tiles.getTiles(TreasureX,TreasureY));
        tiles.setTiles(TreasureX,TreasureY,88);
    }
    public boolean found(){
        return tiles.getTiles(TreasureX, TreasureY) == 64;
    }
}
