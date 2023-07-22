package sk.tuke.gamestudio.server.webservice.maze.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

@Getter
@Setter
public class Field {
    private final int[][] tiles;
    private  int rowCounts;
    private  int columnCounts;
    private int previousTile = 0;
    private int previousTreasureTile = 0;

    Scanner scanner = new Scanner(System.in);

    public void setTiles(int X,int Y, int value){
        this.tiles[X][Y] = value;
    }
    public int getTiles(int X,int Y){
        return tiles[X][Y];
    }

    public Field() {
        this.rowCounts = setRowSize();
        this.columnCounts = setColumnSize();
        tiles = new int[rowCounts][columnCounts];
        generateMaze(0,0);
    }
    public Field(int rowCounts, int columnCounts) {
        this.rowCounts = rowCounts;
        this.columnCounts = columnCounts;
        tiles = new int[rowCounts][columnCounts];
        generateMaze(0,0);
    }

    public void display() {
        for (int i = 0; i < columnCounts; i++) {
            // draw the north edge
            for (int j = 0; j < rowCounts; j++) {
                displayCeiling(j,i);
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < rowCounts; j++) {
                displayCell(j,i);
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < rowCounts; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }
    public void displayInvisible() {
        int[][] helpField = new int[rowCounts][columnCounts];
        for (int d = 0; d < columnCounts; d++) {
            for (int f = 0; f < rowCounts; f++) {
                if(tiles[f][d]==64){
                    helpField[f][d]=1;
                    //middle
                    if(d-1>=0){
                        helpField[f][d-1]=1;
                    }
                    if(d+1<=columnCounts-1){
                        helpField[f][d+1]=1;
                    }
                    //right
                    if(f+1<=rowCounts-1&&d+1<=columnCounts-1){
                        helpField[f+1][d+1]=1;
                    }
                    if(f+1<=rowCounts-1){
                        helpField[f+1][d]=1;
                    }
                    if(d-1>=0&&f+1<=rowCounts-1){
                        helpField[f+1][d-1]=1;
                    }
                    //left
                    if(f-1>=0){
                        helpField[f-1][d]=1;
                    }
                    if(f-1>=0&&d-1>=0){
                        helpField[f-1][d-1]=1;
                    }
                    if(f-1>=0&&d+1<=columnCounts-1){
                        helpField[f-1][d+1]=1;
                    }
                   break;
                }
            }
        }
        for ( int i = 0; i < columnCounts; i++) {
            for (int j = 0; j < rowCounts; j++) {
              if(helpField[j][i]==0){
                System.out.print("--- ");
                } else {
                  displayCeiling(j,i);
                  }
            }
            System.out.println("+");
            for (int j = 0; j < rowCounts; j++) {

                if (helpField[j][i] == 0) {
                    System.out.print("--- ");
                } else  {
                    displayCell(j,i);
                }
            }
            System.out.println("|");

        }
        for (int j = 0; j < rowCounts; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }
    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, rowCounts) && between(ny, columnCounts) && (tiles[nx][ny] == 0)) {
                tiles[cx][cy] = tiles[cx][cy] | dir.bit;
                tiles[nx][ny] = tiles[nx][ny] | dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }


    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }
    private void displayCeiling(int j,int i){
        if(tiles[j][i]==64){
            if(previousTile==88){
                System.out.print((previousTreasureTile & 1) == 0 ? "+---" : "+   ");
                return;
            }
            System.out.print((previousTile & 1) == 0 ? "+---" : "+   ");
            return;
        }
        if(tiles[j][i] == 88){
            System.out.print((previousTreasureTile & 1) == 0 ? "+---" : "+   ");
            return;
        }
        System.out.print((tiles[j][i] & 1) == 0 ? "+---" : "+   ");
    }
    private void displayCell(int j,int i){
        if(tiles[j][i]==88){
            System.out.print((previousTreasureTile & 8) == 0 ? "|@  " : " @  ");
            return;
        }
        if(tiles[j][i]==64){
            if(previousTile==88){
                System.out.print((previousTreasureTile & 8) == 0 ? "|5  " : " 5  ");
                return;
            }
            System.out.print((previousTile & 8) == 0 ? "|5  " : " 5  ");
            return;
        }
        System.out.print((tiles[j][i] & 8) == 0 ? "|   " : "    ");
    }
    private int setRowSize(){
        System.out.println("Enter row size.");
        rowCounts = scanner.nextInt();
        if(rowCounts>50|rowCounts<0){
            System.out.println("Too large number.");
            setRowSize();
        }
        return rowCounts;
    }

    private int setColumnSize(){
        System.out.println("Enter column size.");
        columnCounts = scanner.nextInt();
        if(columnCounts>50|columnCounts<0){
            System.out.println("Too large number.");
            setColumnSize();
        }
        return columnCounts;
    }
}
