package uk.ac.tees.mgd.a0083681.mobileuniproject.objects;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.CHEST;

public class Chest extends GameObject{

    public Chest(int x, int y) {
        super(x, y, CHEST);
        initHitbox(64, 35);

        xDrawOffset = (int) (7 * GAME_SCALE);
        yDrawOffset = (int) (12 * GAME_SCALE);
    }
}
