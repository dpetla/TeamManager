package teammanager;

import java.io.File;
import javafx.scene.image.Image;

/**
 *
 * @author dpetla and brunopetla
 */
public class Player implements Comparable<Player> {

    private Name name;
    //players jersey number
    private int num;
    private int weight;
    private int height;
    private int age;
    private Image image;
    // path to the image file
    private String path;
    private Position position;
    private Statistics stats;

    /**
     * default constructor
     */
    public Player() { }

    /**
     * Full Player constructor.
     *
     * @param name player`s name
     * @param n jersey number
     * @param w weight
     * @param h height
     * @param a age
     * @param img profile picture
     * @param pos position
     * @param s statistics
     */
    public Player(Name name, int n, int w, int h, int a, Image img, String path,
            Position pos, Statistics s) {

        this.name = name;
        num = n;
        weight = w;
        height = h;
        age = a;
        image = img;
        this.path = path;
        position = pos;
        this.stats = s;
    }

    public void setName(String n) {
        name.setName(n);
    }

    public void setNum(int n) {
        if (n > 0 && n < 100)
            num = n;
    }

    public void setWeight(int w) {
        if (w > 0 && w < 200)
            weight = w;
    }

    public void setAge(int a) {
        if (a > 0 && a < 100)
            age = a;
    }

    public void setHeight(int h) {
        if (h > 0 && h < 230)
            height = h;
    }

    public String getName() {
        return this.name.toString();
    }

    public String getFirstName() {
        return this.name.getFirstName();
    }

    public String getLastName() {
        return this.name.getLastName();
    }

    public int getWeight() {
        return weight;
    }

    public int getNum() {
        return num;
    }

    public int getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    public Image getImage() {
        return image;
    }

    public Position getPosition() {
        return position;
    }

    public Statistics getStats() {
        return stats;
    }

    public String getPath() {
        return path;
    }

    /**
     * user has to choose from enum class GOALKEEPER, DEFENSE, MIDFIELDER,
     * STRIKER;
     *
     * @param p player position
     */
    public void setPosition(Position p) {
        
        // need to figure out a way to check if option is valid
        if (true)
            position = p;
    }

    /**
     *
     * @param s
     * @return
     */
    public static Player valueOf(String s) {

        // temporary array to hold strings from input
        String[] temp;

        // varibles to recive input from after parsing each string
        String firstName = null, lastName = null;
        
        int num = 0, weight = 0, height = 0, age = 0, p = 0;
        int gamesPlayed = 0, goals = 0, assist = 0, shotToGoal = 0;
        int tackles = 0, wrongPass = 0, faults = 0;
        int yellowCard = 0, redCard = 0;
        
        String pathName = null;
        File file;
        Image image = null;
        Position pos = null;

        try {

            //store string each piece of the string in a temporary array
            temp = s.split(",");

            // read number, name
            num = Integer.parseInt(temp[0]);
            firstName = temp[1];
            lastName = temp[2];

            // get position and convert to enum
            p = Integer.parseInt(temp[3]);
            switch (p) {
                
                case 1:
                    pos = Position.DEFENSE;
                    break;
                    
                case 2:
                    pos = Position.GOALKEEPER;
                    break;
                    
                case 3:
                    pos = Position.MIDFIELDER;
                    break;
                    
                case 4:
                    pos = Position.STRIKER;
                    break;  
            }

            // read weight, height and age
            weight = Integer.parseInt(temp[4]);
            height = Integer.parseInt(temp[5]);
            age = Integer.parseInt(temp[6]);

            // read the image
            pathName = temp[7];
            file = new File("imgs/" + pathName);
            image = new Image(file.toURI().toString());

            // read all the stats
            gamesPlayed = Integer.parseInt(temp[8]);
            goals = Integer.parseInt(temp[9]);
            assist = Integer.parseInt(temp[10]);
            shotToGoal = Integer.parseInt(temp[11]);
            tackles = Integer.parseInt(temp[12]);
            wrongPass = Integer.parseInt(temp[13]);
            faults = Integer.parseInt(temp[14]);
            yellowCard = Integer.parseInt(temp[15]);
            redCard = Integer.parseInt(temp[16]);

        } catch (Exception ex) {
            System.out.println("Data is not in the correct format or some data is missing");
        }

        // create name instance
        Name fn = new Name(firstName, lastName);

        // create statistics instance
        Statistics st = new Statistics(gamesPlayed, goals, assist, shotToGoal,
                tackles, wrongPass, faults, yellowCard,
                redCard);

        //create a player instance
        Player pl = new Player(fn, num, weight, height, age, image, pathName,
                pos, st);

        return pl;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return name.toString();
    }

    /**
     * Compare players alphabetically.
     * 
     * @param other
     * @return
     */
    @Override
    public int compareTo(Player other) {

        //check if object is null
        if (other == null) throw new NullPointerException();

        //check if both object s being compared are the same
        if (this == other) return 0;

        //compare by name
        return this.getName().compareTo(other.getName());
    }
}
