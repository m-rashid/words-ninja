import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame implements ActionListener {

    //declaring and ininitalizing variables used by two or more components  
    private static TileCollection tileCollectionInstance = new TileCollection();
    private static TileGUI[][] tile = new TileGUI[6][6];
    private static String current = "";
    private static int score = 0;
    private ArrayList<Integer> temp_score = new ArrayList<Integer>();
    private JTextField scoreView;
    private static ArrayList<String> neighbors = new ArrayList<String>();
    private static ArrayList<String> coordinates = new ArrayList<String>();
    private static Color[] colors = new Color[]{
        Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN,
        Color.BLACK, Color.PINK, Color.ORANGE, Color.CYAN, Color.GRAY};
    private Random rand = new Random();
    private static JButton undo = new JButton("Undo");
    private static ArrayList<String> unselectedTiles = new ArrayList<String>();
    private static ArrayList<String> allCoordinates = new ArrayList<String>();
    private static JLabel label1 = new JLabel();
    private static JPanel centre = new JPanel();
    private Timer timer = null;
    private final JLabel label = new JLabel("0");
    private int seconds = 0;

    public Game() {

        //Title of the JFrame
        this.setTitle("WORD FORMATIONS");

        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel for placing buttons at the top
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());

        //panel for displaying the score
        JPanel left = new JPanel();
        //left.setLayout(new FlowLayout());
        left.setBackground(Color.LIGHT_GRAY);

        left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
        left.add(Box.createVerticalGlue());
        //left.add(scoreView);
        left.add(Box.createVerticalGlue());

        left.add(label);

        //creating the buttons
        JButton submit = new JButton("Submit");
        submit.addActionListener(this);
        top.add(submit);

        undo.addActionListener(this);
        top.add(undo);

        JButton reset = new JButton("Shuffle");
        reset.addActionListener(this);
        top.add(reset);

        JButton help = new JButton("Help");
        help.addActionListener(this);
        top.add(help);

        JButton done = new JButton("Quit");
        done.addActionListener(this);
        top.add(done);

        this.add(top, BorderLayout.NORTH);

        this.add(left, BorderLayout.EAST);

        this.add(centre, BorderLayout.CENTER);
        centre.setLayout(new GridLayout(6, 6, 4, 4));

        scoreView = new JTextField(10);
        left.add(scoreView);
        scoreView.setText("Current score: 0");
        scoreView.setAlignmentX(Component.CENTER_ALIGNMENT);
        //scoreView.setAlignmentY(Component.CENTER_ALIGNMENT);
        scoreView.setEditable(false);
        scoreView.setFont(new Font("Serif", Font.BOLD, 17));

        //setting each Tile's command to have its coordinates   
        for (int i = 0; i < 6; i++) {
            for (int k = 0; k < 6; k++) {
                tile[i][k] = new TileGUI(tileCollectionInstance.removeOne());
                centre.add(tile[i][k]);
                tile[i][k].addActionListener(this);
                tile[i][k].setActionCommand(i + "," + k);
            }
        }
        //implementing the timer..  
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText(String.valueOf(seconds));
                seconds++;
                if (seconds == 91) {

                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Time's up! Your score is: " + score, "", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);

                }
            }
        });
        timer.start();

    }

    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();

        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 6; k++) {
                allCoordinates.add(j + "" + k); //adding all the coordinates to the arraylist
                //used for checking when no more words can be formed            
            }
        }

        if (!choice.equals("Done") && !choice.equals("Submit") && !choice.equals("Undo") && !choice.equals("Help") && !choice.equals("Quit") && !choice.equals("Shuffle")); //referring to the Tiles at the centre
        {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {

                    if (tile[j][k] == e.getSource()) {
                        current += (tile[j][k].getTile().letter());
                        temp_score.add(tile[j][k].getTile().value());
                        coordinates.add(j + "" + k);
                        tile[j][k].setBackground(Color.blue);

                        int randint = rand.nextInt(4);

                        tile[j][k].setEnabled(false); //a tile can only be selected once

                        if (neighbors.isEmpty()) //checking for the neighbouring tiles
                        {

                            for (int row = j - 1; row <= j + 1; row++) {
                                for (int col = k - 1; col <= k + 1; col++) {
                                    if (!(j == row && k == col) && row >= 0 && col >= 0 && row < 6 && col < 6) {
                                        neighbors.add("" + row + col);
                                        System.out.println(neighbors);
                                    }
                                }
                            }
                        } else if (neighbors.contains("" + j + k)) {
                            neighbors.clear();
                            for (int row = j - 1; row <= j + 1; row++) {
                                for (int col = k - 1; col <= k + 1; col++) {
                                    if (!(j == row && k == col) && row >= 0 && col >= 0 && row < 6 && col < 6) {
                                        neighbors.add("" + row + col);
                                        System.out.println(neighbors);
                                    }
                                }
                            }

                        } else //if Tile selected is not an adjacent tile
                        {
                            neighbors.clear();
                            current = current.substring(0, current.length() - 1);

                            JOptionPane.showMessageDialog(null, "Illegal move", "", JOptionPane.INFORMATION_MESSAGE);

                            String lastClicked = (coordinates.get(coordinates.size() - 1)); //enabling the illegal move tile
                            int x = Character.getNumericValue(lastClicked.charAt(0));
                            int y = Character.getNumericValue(lastClicked.charAt(1));

                            tile[x][y].setEnabled(true);
                            tile[x][y].setBackground(null);

                            coordinates.remove(coordinates.size() - 1);
                            neighbors.clear();
                            if (coordinates.size() == 0) {
                                neighbors.clear();
                            }
                            String SecondlastClicked = (coordinates.get(coordinates.size() - 1));
                            int secondX = Character.getNumericValue(SecondlastClicked.charAt(0));
                            int secondY = Character.getNumericValue(SecondlastClicked.charAt(1));

                            //removing the neighbors of the illegally selected Tile
                            for (int row = -1; row <= secondX + 1; row++) {
                                for (int col = secondY - 1; col <= secondY + 1; col++) {
                                    if (!(secondX == row && secondY == col) && row >= 0 && col >= 0 && row < 6 && col < 6) {
                                        neighbors.add("" + row + col);

                                    }
                                }
                            }

                        }

                    }
                }
            }
            undo.setEnabled(true); //tile can be unselected after making a move
        }

        if (choice.equals("Submit")) //when submit button is clicked to submit the formed word
        {
            Random randint = new Random(); //to get a random colour
            int random = randint.nextInt(8);
            label:
            try {

                String[] dict = FileToArray.read("EnglishWords.txt"); //iterating through the dictionary to check if the formed word exists
                int n = 0;
                for (String u : dict) {

                    if (current.equalsIgnoreCase(u)) {

                        for (int k = 0; k < temp_score.size(); k++) {
                            score += temp_score.get(k);
                        }
                        for (String m : coordinates) {

                            int a = Character.getNumericValue(m.charAt(0));
                            int b = Character.getNumericValue(m.charAt(1));
                            tile[a][b].setBackground(colors[random]);  //set the colour of the formed word
                        }
                        n++;
                        
                        /*
                        //replacing the tiles with new ones
                        for (String m : coordinates) {

                            int a = Character.getNumericValue(m.charAt(0));
                            int b = Character.getNumericValue(m.charAt(1));
                            System.out.println(a+""+b);
                            centre.remove(tile[a][b]);

                            tile[a][b] = new TileGUI(tileCollectionInstance.removeOne());
                            centre.add(tile[a][b]);
                            tile[a][b].addActionListener(this);
                            tile[a][b].setActionCommand(a + "," + b);

                        }
                        */
                        

                    }

                }


                if (current.length() == 1) //if only one letter is selected, and submitted
                {
                    JOptionPane.showMessageDialog(null, "Oops, formed word does not exists. Try again", "", JOptionPane.INFORMATION_MESSAGE);
                    for (String m : coordinates) //iterating through the coordinates of selected tiles
                    {
                        int x = Character.getNumericValue(m.charAt(0));
                        int y = Character.getNumericValue(m.charAt(1));

                        tile[x][y].setEnabled(true); //setting the tiles back to their original forms
                        tile[x][y].setBackground(null);

                    }
                    break label;
                }

                if (current.length() == 0) //if only one letter is selected, and submitted
                {
                    JOptionPane.showMessageDialog(null, "Please form a word and click Submit", "", JOptionPane.INFORMATION_MESSAGE);
                    for (String m : coordinates) //iterating through the coordinates of selected tiles
                    {
                        int x = Character.getNumericValue(m.charAt(0));
                        int y = Character.getNumericValue(m.charAt(1));

                        tile[x][y].setEnabled(true); //setting the tiles back to their original forms
                        tile[x][y].setBackground(null);

                    }
                    break label;
                }

                undo.setEnabled(true); //tile can be unselected after making a move

                if (n == 0) //if word does not exist
                {
                    JOptionPane.showMessageDialog(null, "oops, formed word does not exist. Try again", "", JOptionPane.INFORMATION_MESSAGE);
                    for (String m : coordinates) {

                        int x = Character.getNumericValue(m.charAt(0));
                        int y = Character.getNumericValue(m.charAt(1));

                        tile[x][y].setEnabled(true);  //setting the tiles of the letters to unselected
                        tile[x][y].setBackground(null);
                    }
                    neighbors.clear();

                    break label;

                }

                scoreView.setText("Current score: " + score);
                n = 0;

            } catch (Exception exception) {
                System.out.println("File not found");
            }
            neighbors.clear();

            current = "";
            temp_score.clear();
            coordinates.clear();
            undo.setEnabled(true);
            unselectedTiles.clear();
        }

        if (choice.equals("Quit")) //when the user chooses to stop playing the game
        {

            if (score == 0) {
                JOptionPane.showMessageDialog(null, "Your score is 0. Take this seriously, it's fun.", "", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            if (score <= 5) {
                JOptionPane.showMessageDialog(null, "You scored " + score + ". You can do better :)", "", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            if (score <= 10) {
                JOptionPane.showMessageDialog(null, "You scored " + score + ". You can do better :)", "", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            if (score > 10) {
                JOptionPane.showMessageDialog(null, "You scored " + score + ". Well done :)", "", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

        }

        if (choice.equals("Undo")) //unselecting the last selected tile
        {
            String lastClicked = (coordinates.get(coordinates.size() - 1));
            int x = Character.getNumericValue(lastClicked.charAt(0));
            int y = Character.getNumericValue(lastClicked.charAt(1));

            tile[x][y].setEnabled(true);
            tile[x][y].setBackground(null);
            neighbors.clear();
            coordinates.remove(coordinates.size() - 1);
            undo.setEnabled(false);  //only the last selected tile can be unselected

            if (coordinates.size() == 0) {
                neighbors.clear();
            }

            for (int i = 0; i < current.length(); i++) //removing the undone letter from the word
            {
                current = current.substring(0, current.length() - 1);

                break;
            }
            //removing the neighbors of the unselected Tile from the ArrayList
            String SecondlastClicked = (coordinates.get(coordinates.size() - 1));
            int secondX = Character.getNumericValue(SecondlastClicked.charAt(0));
            int secondY = Character.getNumericValue(SecondlastClicked.charAt(1));

            for (int row = -1; row <= secondX + 1; row++) {
                for (int col = secondY - 1; col <= secondY + 1; col++) {
                    if (!(secondX == row && secondY == col) && row >= 0 && col >= 0 && row < 6 && col < 6) {
                        neighbors.add("" + row + col);

                    }
                }
            }

        }

        if (choice.equals("Shuffle")) {
            TileCollection AnothertileCollectionInstance = new TileCollection();

            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    centre.remove(tile[j][k]);
                    tile[j][k] = new TileGUI(AnothertileCollectionInstance.removeOne());
                    centre.add(tile[j][k]);
                    tile[j][k].addActionListener(this);
                    neighbors.clear();
                    coordinates.clear();

                }
            }

        }

        if (choice.equals("Help")) //instructions on how to play the game
        {
            timer.stop();
            centre.setVisible(false);

            String pt1 = "<html><body width=500";
            String pt2
                    = "<h2>Welcome to the Words Formation game.</h2>"
                    + "<p>A 6X6 grid of randomly letters will be given to you"
                    + " with the scores of each letter displayed on the tiles. "
                    + " Your task is to form words using the letters in 90 seconds"
                    + " with an aim of gaining a maximum score<br><br>"
                    + "<p>Instructions:<br> "
                    + "Select letters by clicking on the respective tiles to form words<br>"
                    + "Words may only be formed from sequences of adjacent tiles<br>"
                    + "Two tiles are adjacent if their edges or corners meet<br>"
                    + "A tile may be used in at most one word<br>"
                    + "The game will stop after 90 seconds<br><br>"
                    + "Button operations:<br>"
                    + "Submit: Submit the word formed to update the current score<br>"
                    + "Shuffle: Shuffle the letters to bring in new ones<br>"
                    + "Undo: Unselect the last button clicked<br>"
                    + "Quit: End the game<br>";

            String s = pt1 + pt2;

            JOptionPane.showMessageDialog(null, s);
            timer.start();
            centre.setVisible(true);
        }

    }
}
