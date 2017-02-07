package teammanager;

/**
 *
 * @author dpetla and brunopetla
 */
public class Statistics {

    // data members for player`s statistics
    private int gamesPlayed;
    private int goals;
    private int assist;
    private int shotToGoal;
    private int tackles;
    private int wrongPass;
    private int faults;
    private int yellowCard;
    private int redCard;

    // default constructor    
    public Statistics() {
    }

    /**
     * constructor with all player`s data members
     *
     * @param gamesPlayed number of games each player has played
     * @param goals number of goals player has scored
     * @param assist number of assistances a player has done
     * @param shotToGoal number of shots to goal
     * @param tackles number of tackles a player has done
     * @param wrongPass number of wrong passes a player has done
     * @param faults number of faults a player receives
     * @param yellowCard number of yellow cards a player received
     * @param redCard number of red cards a player received
     */
    public Statistics(int gamesPlayed, int goals, int assist, int shotToGoal,
            int tackles, int wrongPass, int faults, int yellowCard,
            int redCard) {
        this.gamesPlayed = gamesPlayed;
        this.goals = goals;
        this.assist = assist;
        this.shotToGoal = shotToGoal;
        this.tackles = tackles;
        this.wrongPass = wrongPass;
        this.faults = faults;
        this.yellowCard = yellowCard;
        this.redCard = redCard;
    }

    // getters for player`s statistics
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssist() {
        return assist;
    }

    public int getShotToGoal() {
        return shotToGoal;
    }

    public int getTackles() {
        return tackles;
    }

    public int getWrongPass() {
        return wrongPass;
    }

    public int getFaults() {
        return faults;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public int getRedCard() {
        return redCard;
    }

    // setters for player`s statistics
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public void setShotToGoal(int shotToGoal) {
        this.shotToGoal = shotToGoal;
    }

    public void setTackles(int tackles) {
        this.tackles = tackles;
    }

    public void setWrongPass(int wrongPass) {
        this.wrongPass = wrongPass;
    }

    public void setFaults(int faults) {
        this.faults = faults;
    }

    public void setYellowCard(int yellowCard) {
        this.yellowCard = yellowCard;
    }

    public void setRedCard(int redCard) {
        this.redCard = redCard;
    }

}
