package uk.ac.tees.mgd.a0083681.mobileuniproject.helpers;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;

public class GameConstants {

    public static class AllConstants{
        public static final int GAME_SCALE = 3;
    }

    public static class LevelConstants{
        public static final int ONE = 0;
        public static final int TWO = 1;
        public static final int THREE = 2;
        public static final int FOUR = 3;
    }

    public static class EnemyConstants {
        public static final int CRAB = 0;
        public static final int STARFISH = 1;
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH = (int) (26 * GAME_SCALE);
        public static final int CRABBY_HEIGHT = (int) (19 * GAME_SCALE);
        public static int CRAB_DRAWOFFSET_X = (int) 26 * GAME_SCALE;
        public static int CRAB_DRAWOFFSET_Y = (int) 9 * GAME_SCALE;

        public static final int STARFISH_WIDTH = (int) (9 * GAME_SCALE);
        public static final int STARFISH_HEIGHT = (int) (21 * GAME_SCALE);
        public static final int STARFISH_DRAWOFFSET_X = (int) (9 * GAME_SCALE);
        public static final int STARFISH_DRAWOFFSET_Y = (int) (7 * GAME_SCALE);



        public static int GetSpriteAmount(int enemy_type, int enemy_state) {

            switch (enemy_type) {
                case CRAB:
                    switch (enemy_state) {
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
                case STARFISH:
                    switch (enemy_state){
                        case IDLE:
                            return 8;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
            }

            return 0;

        }

        public static int GetMaxHealth(int enemy_type){
            switch (enemy_type){
                case CRAB:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemy_type){
            switch (enemy_type){
                case CRAB:
                    return 15;
                case STARFISH:
                    return 20;
                default:
                    return 0;
            }
        }
    }

    public static class Directions{
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
    }

    public static class ButtonConstants{
        public static final int PLAYBTN = 0;
        public static final int LEFTBTN = 0;
        public static final int RIGHTBTN = 0;
        public static final int ABTN = 0;
        public static final int BBTN = 0;
        public static final int QUITBTN = 2;
        public static final int BACKTOMAINMENU = 2;
        public static final int RESTART = 1;
        public static final int UNPAUSE = 0;
        public static final int BUTTON_SCALE = 7;

    }

    public static class PlayerConstants {


        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_2 = 7;
        public static final int ATTACK_3 = 8;
        public static final int ATTACK_JUMP_1 = 9;
        public static final int ATTACK_JUMP_2 = 10;
        public static final int HIT_GROUND = 11;
        public static final int DEAD_GROUND = 12;
        public static final int SHAKE_ATTACK = 13;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                case HIT_GROUND:
                case DEAD_GROUND:
                    return 4;
                case JUMP:
                case ATTACK_1:
                case ATTACK_2:
                case ATTACK_3:
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                case SHAKE_ATTACK:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }

    public static class ObjectConstants {

        public static final int CHEST = 0;
        public static final int SPIKES = 1;

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case CHEST:
                    return 7;
            }
            return 1;
        }
    }
}
