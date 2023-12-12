package uk.ac.tees.mgd.a0083681.mobileuniproject.helpers;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.TILES_SIZE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_HEIGHT;

import android.graphics.PointF;
import android.graphics.RectF;

import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.CustomButton;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int [][] lvlData){
        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (!IsSolid(x + width, y, lvlData)) {
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= GAME_HEIGHT)
            return true;
        float xIndex = x / TILES_SIZE;
        float yIndex = y / TILES_SIZE;

        return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData){
        int value = lvlData[(int) yTile][(int) xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static float GetEntityXPosNextToWall(RectF hitbox, float xSpeed){
        int currentTile = (int) (hitbox.left / TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * TILES_SIZE;
            int xOffset = (int) (TILES_SIZE - hitbox.width());
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(RectF hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.top / TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitbox.height());
            return tileYPos + yOffset;
        } else
            // Jumping
            return currentTile * TILES_SIZE;
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

    public static boolean AreAllTilesWalkable(int xStart, int xEnd, int y, int [][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }
        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, RectF hitbox, RectF hitbox2,int yTile){
        int firstXTile = (int)hitbox.left / TILES_SIZE;
        int secondXTile = (int)hitbox2.left / TILES_SIZE;
        if (firstXTile > secondXTile)
            return AreAllTilesWalkable(secondXTile,firstXTile,yTile,lvlData);
        else
            return AreAllTilesWalkable(firstXTile,secondXTile,yTile,lvlData);
    }
}
