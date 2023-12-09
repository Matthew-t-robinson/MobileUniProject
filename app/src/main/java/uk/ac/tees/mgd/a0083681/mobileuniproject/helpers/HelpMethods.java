package uk.ac.tees.mgd.a0083681.mobileuniproject.helpers;

import static android.content.Context.TELECOM_SERVICE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;
import android.graphics.RectF;

import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.CustomButton;
import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.Player;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int [][] lvlData, boolean crab){
        if (crab)
            System.out.println("here 1");
        if (!IsSolid(x, y, lvlData)) {
            if (crab)
                System.out.println("here 2");
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (crab)
                    System.out.println("here 3");
                if (!IsSolid(x + width, y, lvlData)) {
                    if (crab)
                        System.out.println("here 4");
                    if (!IsSolid(x, y + height, lvlData)) {
                        if (crab)
                            System.out.println("here 5");
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * 96;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= GAME_HEIGHT)
            return true;
        float xIndex = x / 96;
        float yIndex = y / 96;

        return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData){
        int value = lvlData[(int) yTile][(int) xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static float GetEntityXPosNextToWall(RectF hitbox, float xSpeed){
        int currentTile = (int) (hitbox.left / 96);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * 96;
            int xOffset = (int) (96 - hitbox.width());
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * 96;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(RectF hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.top / 96);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * 96;
            int yOffset = (int) (96 - hitbox.height());
            return tileYPos + yOffset;
        } else
            // Jumping
            return currentTile * 96;
    }

    public static boolean IsEntityOnFloor(RectF hitbox, int[][] lvlData) {
        if (!IsSolid(hitbox.left, hitbox.top + hitbox.height() + 1, lvlData))
            if (!IsSolid(hitbox.left + hitbox.width(), hitbox.top + hitbox.height() + 1, lvlData))
                return false;

        return true;
    }

    public static boolean isIn(PointF pos, CustomButton b){
        return b.getHitbox().contains(pos.x, pos.y);
    }

    public static boolean IsFloor(RectF hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0)
            return IsSolid(hitbox.right + xSpeed, hitbox.bottom + 1, lvlData);
        else
            return IsSolid(hitbox.left + xSpeed, hitbox.top + hitbox.height(), lvlData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int [][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }
        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, RectF hitbox, RectF hitbox2,int yTile){
        int firstXTile = (int)hitbox.left / 96;
        int secondXTile = (int)hitbox2.left / 96;
        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile,firstXTile,yTile,lvlData);
        else
            return IsAllTilesWalkable(firstXTile,secondXTile,yTile,lvlData);
    }
}
