package uk.ac.tees.mgd.a0083681.mobileuniproject.objects;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.CHEST;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.CHEST_HEIGHT;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.CHEST_WIDTH;

public class Chest extends GameObject{

    public Chest(int x, int y) {
        super(x, y, CHEST);
        initHitbox(CHEST_WIDTH, CHEST_HEIGHT);

        xDrawOffset = 7 * GAME_SCALE;
        yDrawOffset = 12 * GAME_SCALE;
    }
}
