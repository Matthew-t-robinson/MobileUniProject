package uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates;

import uk.ac.tees.mgd.a0083681.mobileuniproject.Sound.AudioManager;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.Game;

public class BaseState {
    protected Game game;
    public AudioManager audioManager = new AudioManager();
    public BaseState(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }
}
