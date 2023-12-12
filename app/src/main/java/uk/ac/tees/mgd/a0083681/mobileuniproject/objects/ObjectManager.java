package uk.ac.tees.mgd.a0083681.mobileuniproject.objects;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.SFXConstants.CHESTOPEN;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.Playing;
import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.Player;
import uk.ac.tees.mgd.a0083681.mobileuniproject.levels.LoadLevelManager;
import uk.ac.tees.mgd.a0083681.mobileuniproject.levels.levelManager;
import uk.ac.tees.mgd.a0083681.mobileuniproject.levels.levelSpriteManager;
import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.entitySpriteManager;

public class ObjectManager {
    private final Playing playing;
    private final entitySpriteManager ChestSpriteManager = entitySpriteManager.CHEST;
    private final entitySpriteManager SpikeSpriteManager = entitySpriteManager.SPIKES;
    private ArrayList<Chest> Chests;
    private ArrayList<Spikes> Spikes;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadObjects(levelManager.currentLevel.getLevel());
    }

    public void loadObjects(LoadLevelManager currentLevel){
        Chests = currentLevel.getChestList();
        Spikes = currentLevel.getSpikesList();
    }

    public void update(){
        for (Chest c : Chests) {
            c.update();
            if (c.isAnimationDone()) {
                playing.setLevelCompleted(true);
            }
        }
    }

    public void draw(Canvas c, int xLvlOffset) {
        for (Chest currentChest : Chests) {
            c.drawBitmap(
                    ChestSpriteManager.getSprite(0, currentChest.getAniIndex()),
                    currentChest.getHitbox().left - xLvlOffset,
                    currentChest.getHitbox().top,
                    null);
        }
        for (Spikes s : Spikes){
            c.drawBitmap(
                    SpikeSpriteManager.getSprite(0, s.getAniIndex()),
                    s.getHitbox().left - xLvlOffset,
                    s.getHitbox().top,
                    null);
        }
    }

    public void checkObjectHit(RectF attackBox) {
        for (Chest c : Chests)
        {
            if (RectF.intersects(c.getHitbox(),attackBox)) {
                playing.audioManager.ToggleSoundEffect(CHESTOPEN);
                c.setAnimation(true);
            }
        }
    }

    public void checkSpikesTouched(Player player) {
        for (Spikes s : Spikes)
            if (RectF.intersects(s.getHitbox(),player.getHitbox()))
                player.kill();
    }

    public void reset(){
        for (Chest c : Chests){
            c.reset();
        }
    }
}
