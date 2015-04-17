// Original code by Clara James
// Enhancements made by Christopher Bahn
/**
 * Created by christopherbahn on 4/15/15.
 */

import java.util.*;
public class Block {

    private static int blockX; // This code creates X/Y coordinates for three impassable blocks. This is the square number (not pixel)
    private static int blockY;
    protected static LinkedList<Integer> blocksListX = new LinkedList<Integer>();
    protected static LinkedList<Integer> blocksListY = new LinkedList<Integer>();
    public static boolean blocksOn = true;
    public static int howManyBlocks = 5;
    public Block(Snake s){
        placeBlock(s);
    }

    //keytype 'b' toggles the blocks on or off
    public static void blocksOn() {
        if (blocksOn) {
            blocksOn = false;
        } else {
            blocksOn = true;
        }
    }

    //returns the status of the WarpWallOn variable
    public static String areBlocksOn() {
        if (blocksOn) {
            return "ON";
        } else {
            return "OFF";
        }
    }


    protected void placeBlock(Snake s){
        Random rng = new Random();
        boolean blockInSnake = true;
        while (blockInSnake == true) {
            //Generate random block locations
            for (int i = 0; i < howManyBlocks; i++) {
                blockX = rng.nextInt(SnakeGame.xSquares);
                blockY = rng.nextInt(SnakeGame.ySquares);
                blockInSnake = Snake.isSnakeSegment(blockX, blockY); //Is block inside the Snake? It shouldn't be
                if (!blockInSnake) {
                    blocksListX.add(blockX);
                    blocksListY.add(blockY);
                }
            }
        }
    }


    public static int getBlockX() {
        return blockX;
    }

    public static void setBlockX(int blockX) {
        Block.blockX = blockX;
    }

    public static int getBlockY() {
        return blockY;
    }

    public static void setBlockY(int blockY) {
        Block.blockY = blockY;
    }

    public static boolean isBlocksOn() {
        return blocksOn;
    }

    public static void setBlocksOn(boolean blocksOn) {
        Block.blocksOn = blocksOn;
    }

    public static int getHowManyBlocks() {
        return howManyBlocks;
    }

    public static void setHowManyBlocks(int howManyBlocks) {
        Block.howManyBlocks = howManyBlocks;
    }
}