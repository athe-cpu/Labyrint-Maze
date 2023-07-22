package maze.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.maze.core.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldTest {
    private  int rowCounts;
    private  int columnCounts;
    Field field ;
    @BeforeEach
    void setUp() {
        rowCounts = 5;
        columnCounts = 5;
        field = new Field(5,5);
    }
    @Test
     void setRowSize() {
        int expectedRows = -1;
          if(rowCounts<50&&rowCounts>0) {
               expectedRows = rowCounts;
              assertEquals(expectedRows,rowCounts);
          }else {
              assertEquals(expectedRows,rowCounts);
          }

    }
    @Test
    void setColumnSize() {
        int expectedColumns = -1;
        if(columnCounts<50&&columnCounts>0) {
            expectedColumns = columnCounts;
           assertEquals(expectedColumns,columnCounts);
        }else {
            assertEquals(expectedColumns,columnCounts);
        }

    }
    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }
    @Test
    @DisplayName("Simple comprise should work")
    void testBetween() {
        assertTrue(between(4, 5), "Regular comprise should work");
    }

    boolean possibleNumber(int value){
        int[] possible = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        for (int i = 0; i<15;i++){
            if(possible[i] != value){
            }else {
                return true;
            }
        }
        return false;
    }
    @Test
    @DisplayName("Check on possible numbers in maze")
    void testGenerate() {
        boolean left =false;
        boolean top =false;
        boolean bottom =false;
        boolean right = false;
        for (int i = 0; i<field.getColumnCounts();i++) {
            for (int j = 0; j < field.getRowCounts(); j++) {
                if((field.getTiles(j,i)&8)==0){
                    left=true;
                }
                if((field.getTiles(j,i)&1)==0){
                    top=true;
                }
                if(i+1<5) {
                    if ((field.getTiles(j, i + 1) & 1) == 0) {
                        bottom = true;
                    }
                }
                if(j+1<5) {
                    if ((field.getTiles(j + 1, i) & 8) == 0) {
                        right = true;
                    }
                }
                if (left&&top&&bottom&&right){
                    assertEquals(1,0);
                }else {
                    assertEquals(1,1);
                }
                 left =false;
                 top =false;
                 bottom =false;
                 right = false;
            }
        }
        for (int i = 0; i<field.getColumnCounts();i++){
            for (int j = 0; j<field.getRowCounts();j++){
                assertTrue( possibleNumber(field.getTiles(i,j)),"Should be true");
            }
        }
    }
}