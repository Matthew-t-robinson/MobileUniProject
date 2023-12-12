package uk.ac.tees.mgd.a0083681.mobileuniproject.Sound;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.SFXConstants.*;

import android.media.MediaPlayer;
import uk.ac.tees.mgd.a0083681.mobileuniproject.R;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity;

public class AudioManager {
    private final MediaPlayer jump = MediaPlayer.create(MainActivity.getGameContext(), R.raw.sfx_movement_jump13);
    private final MediaPlayer land = MediaPlayer.create(MainActivity.getGameContext(), R.raw.sfx_movement_jump13_landing);
    private final MediaPlayer playerAttack = MediaPlayer.create(MainActivity.getGameContext(), R.raw.sfx_wpn_sword2);
    private final MediaPlayer playerShakeAttack = MediaPlayer.create(MainActivity.getGameContext(), R.raw.sfx_wpn_punch4);
    private final MediaPlayer enemyAttack = MediaPlayer.create(MainActivity.getGameContext(), R.raw.sfx_weapon_singleshot3);
    private final MediaPlayer shakeStun = MediaPlayer.create(MainActivity.getGameContext(), R.raw.sfx_exp_short_hard14);
    private final MediaPlayer openChest = MediaPlayer.create(MainActivity.getGameContext(),R.raw.sfx_sounds_fanfare3);
    private final MediaPlayer enemyDie = MediaPlayer.create(MainActivity.getGameContext(), R.raw.sfx_deathscream_alien2);
    private final MediaPlayer playerDie = MediaPlayer.create(MainActivity.getGameContext(), R.raw.sfx_deathscream_human14);

    public void ToggleSoundEffect(int SFX)
    {
        switch (SFX){
            case PLAYERJUMP:
                jump.start();
                break;
            case PLAYERATTACK:
                playerAttack.start();
                break;
            case STUNSHAKE:
                shakeStun.start();
                break;
            case STUNATTACK:
                playerShakeAttack.start();
                break;
            case PLAYERDIE:
                playerDie.start();
                break;
            case ENEMYDIE:
                enemyDie.start();
                break;
            case PLAYERLAND:
                land.start();
                break;
            case ENEMYATTACK:
                enemyAttack.start();
                break;
            case CHESTOPEN:
                openChest.start();
                break;

        }
    }
}
