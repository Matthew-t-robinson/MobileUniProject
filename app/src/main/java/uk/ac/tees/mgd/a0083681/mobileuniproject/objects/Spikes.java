package uk.ac.tees.mgd.a0083681.mobileuniproject.objects;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.*;

public class Spikes extends GameObject{
    public Spikes(int x, int y) {
        super(x, y, SPIKES);

        initHitbox(SPIKES_WIDTH, SPIKES_HEIGHT);
    }
}
