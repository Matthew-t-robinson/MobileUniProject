package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.*;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.CountDownTimer;

import java.util.ArrayList;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.Playing;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.BitmapMethods;

public class EnemyManager {

    private Playing playing;
    private final entitySpriteManager Crab;
    private final entitySpriteManager Starfish;
    private ArrayList<Crab> crabs = new ArrayList<>();
    private ArrayList<Starfish> starfish = new ArrayList<>();
    private boolean Stunning;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        Crab = entitySpriteManager.CRAB;
        Starfish = entitySpriteManager.STARFISH;

        addEnemies();
    }

    public void addEnemies() {
        crabs = playing.getLvlManager().currentLevel.getLevel().GetCrabs();
        starfish = playing.getLvlManager().currentLevel.getLevel().GetStarfish();
        //System.out.println("Size of crabs:" + crabs.size());
    }

    public void Update(double delta, Player player){
        for (Crab c : crabs){
            if (c.isActive())
                c.update(playing.getLvlManager().getCurrentLevel().getLvlData(), delta, player);
        }
        for (Starfish s : starfish){
            if (s.isActive())
                s.update(playing.getLvlManager().getCurrentLevel().getLvlData(), delta, player);
        }
    }
    
    public void draw(Canvas c, int xLvlOffset){
        drawCrabs(c, xLvlOffset);
        drawStarfish(c, xLvlOffset);
    }

    private void drawCrabs(Canvas c, int xLvlOffset) {
        for(Crab currentCrab : crabs){
            if (currentCrab.isActive()) {
                //currentCrab.drawHitbox(c, xLvlOffset);
                //currentCrab.drawAttackBox(c, xLvlOffset);
                c.drawBitmap(
                        BitmapMethods.createFlippedBitmap(Crab.getSprite(currentCrab.getEnemyState(), currentCrab.getAniIndex()), currentCrab.isFacingRight(), false),
                        currentCrab.getHitbox().left - xLvlOffset - CRAB_DRAWOFFSET_X, currentCrab.getHitbox().top - CRAB_DRAWOFFSET_Y, null);
            }
        }
    }

    private void drawStarfish(Canvas c, int xLvlOffset) {
        for(Starfish s : starfish){
            if (s.isActive()) {
               // s.drawHitbox(c, xLvlOffset);
               //s.drawAttackBox(c, xLvlOffset);
                c.drawBitmap(
                        BitmapMethods.createFlippedBitmap(Starfish.getSprite(s.getEnemyState(), s.getAniIndex()), s.isFacingRight(), false),
                        s.getHitbox().left - xLvlOffset - STARFISH_DRAWOFFSET_X, s.getHitbox().top - STARFISH_DRAWOFFSET_Y, null);
            }
        }
    }

    public void resetAllEnemies() {
        for (Crab c : crabs)
            c.resetEnemy();
        for (Starfish s : starfish)
            s.resetEnemy();
    }

    public void checkEnemyHit(RectF attackBox){
        for(Crab c: crabs) {
            if (c.isAlive())
                if (RectF.intersects(attackBox,c.getHitbox())) {
                    c.damage(10);
                    return;
                }
        }
    }

    public boolean canShakeAttackHit(Player player){
        for (Starfish s: starfish){
            if (s.isAlive())
                if (RectF.intersects(player.getAttackBox(),s.getHitbox())) {
                    return true;
                }
        }
        return false;
    }

    public void shakeAttackHit(RectF attackBox){
        for (Starfish s: starfish){
            if (s.isActive())
                if (RectF.intersects(attackBox,s.getHitbox())) {
                    s.damage(10);
                    return;
                }
        }
    }

    public void stunTimer(){
        if (!Stunning) {
            Stunning = true;
            CountDownTimer time = new CountDownTimer(6000, 1000) {
                public void onTick(long millisUntilFinished) {
                    for (Crab c : crabs) {
                        if (c.isAlive() && c.isActive())
                            c.setStun(true);
                    }
                }

                @Override
                public void onFinish() {
                    for (Crab c : crabs) {
                        c.setStun(false);
                    }
                    Stunning = false;
                }
            };
            time.start();
        }
    }
}
