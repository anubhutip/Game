/*
 * Game.java
 *
 * Version: 1.0
 *
 * Revisions: 1
 *
 */
package asgfour;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Vector;

/**
 * A program allowing 2 players to guess a name for the image. First correct
 * guess wins. Each player's word and picture filename will be specified in
 * command line
 *
 * @author Anubhuti Puppalwar
 */
public class Game {

    // Image to be produce in every turn is created and stored in these vectors
    private static Vector<String> vFile1ImageinTurns = new Vector<String>();
    private static Vector<String> vFile2ImageinTurns = new Vector<String>();

    // String of dots of word length to display during turns
    private static String meStr = "";
    private static String youStr = "";

    // init char array to '.', then replace by guessed letters
    private static char[] meStrGuessedValue;
    private static char[] youStrGuessedValue;

    // Object of picture classes
    static Picture1 pic1;
    static Picture2 pic2;
    // Object of player classes
    static Player1 playerA;
    static Player2 playerB;

    /**
     * The main program, displays turns and asks to guess a letter and prints
     * some percent of image on correct guess.
     *
     * @param args Each player's word and picture filename will be specified in
     *             command line arg
     * @throws FileNotFoundException If file not found
     */
    public static void main(String[] args1) throws FileNotFoundException {

	String[] args = { "-you", "superman.txt", "Superman", "-me",
		"batman.txt", "Batman" };
	pic1 = new Picture1();
	pic2 = new Picture2();
	playerA = new Player1();
	playerB = new Player2();
	pic1.fillVectorAndReturnNumOfLines();
	pic2.fillVectorAndReturnNumOfLines();

	pic1.findNumOfCharsToShowFile(args[2]);
	initVectorforFile1(pic1);
	pic2.findNumOfCharsToShowFile(args[5]);
	initVectorforFile2(pic2);

	initWords(args[2], args[5]);
	playGame(args[2], args[5]);
	pic1.closeScanner();
	pic2.closeScanner();
	playerA.closeScanner();
	playerB.closeScanner();
    }

    /**
     * Initialize another vector by adding strings of dots(length equal to
     * length of a line in file) into it, as many as there are number of lines
     * in file.
     *
     * @param pic instance of picture class
     */
    private static void initVectorforFile1(Picture pic) {
	String defaultLineOfFile = pic.initElementofvector();
	int lines = pic.getNumofLines();
	while (lines > 0) {
	    vFile1ImageinTurns.add(defaultLineOfFile);
	    lines--;
	}

    }

    /**
     * Initialize another vector by adding strings of dots(length equal to
     * length of a line in file) into it, as many as there are number of lines
     * in file.
     *
     * @param pic instance of picture class
     */
    private static void initVectorforFile2(Picture pic) {
	String defaultLineOfFile = pic.initElementofvector();
	int lines = pic.getNumofLines();
	while (lines > 0) {
	    vFile2ImageinTurns.add(defaultLineOfFile);
	    lines--;
	}

    }

    /**
     * Initializes the word and char array with dots for displaying in each
     * turn. Me is first player, You is second player
     *
     * @param word1 Player1 word
     * @param word2 Player2 word
     */
    private static void initWords(String word1, String word2) {
	meStr = "( ";
	for (int i = 0; i < word1.length(); i++) {
	    meStr += ".";
	}
	meStr += " )";
	meStrGuessedValue = meStr.toCharArray();

	youStr = "( ";
	for (int j = 0; j < word2.length(); j++) {
	    youStr += ".";
	}
	youStr += " )";
	youStrGuessedValue = youStr.toCharArray();

    }

    /**
     * It starts turns for each player to guess a letter until one of the word
     * is completely guessed.
     *
     * @param word1 Player1 word
     * @param word2 Player2 word
     */
    private static void playGame(String word1, String word2) {

	// loop until one of the char array is filled with all correct letters
	while (String.valueOf(meStrGuessedValue).contains(".")
		&& String.valueOf(youStrGuessedValue).contains(".")) {
	    // check guess for player 1
	    checkGuessLetterAndShowMoreChars(meStrGuessedValue, meStr, playerA,
		    word1, pic1);
	    // check guess for player 2
	    if (String.valueOf(meStrGuessedValue).contains(".")
		    && String.valueOf(youStrGuessedValue).contains(".")) {
		checkGuessLetterAndShowMoreChars(youStrGuessedValue, youStr,
			playerB, word2, pic2);
	    }

	}

    }

    /**
     * Checks if the guess is correct, then it replaces vector with new Strings
     * having more characters replaced. Prints new image and also checks if all
     * dots in char array are replced with letters then prints player won.
     *
     * @param strGuessedValue array of chars containing dots and guessed letters
     * @param strDotDisplay   string of dots for displaying in each turn
     * @param player          player whose turn is going on
     * @param word            word to be guessed
     * @param pic             instance of picture class associated with that
     *                        word
     */
    private static void checkGuessLetterAndShowMoreChars(char[] strGuessedValue,
	    String strDotDisplay, Players player, String word, Picture pic) {
	String acceptedInputFromMe;
	// if the letter guessed is also guessed in the past, then guess another
	// letter.
	do {
	    System.out
		    .println(player.getName() + " your turn " + strDotDisplay);
	    acceptedInputFromMe = player.getInput();
	} while ((String.valueOf(strGuessedValue))
		.contains(acceptedInputFromMe));

	// If the word has that letter
	if (word.contains(acceptedInputFromMe)) {
	    // loop it number of times the letter is present in word
	    
	    for (int index = word
		    .indexOf(acceptedInputFromMe); index >= 0; index = word
			    .indexOf(acceptedInputFromMe, index + 1)) {
		
		strGuessedValue[index + 2] = acceptedInputFromMe.charAt(0);
		System.out.println("Your guess was correct : "
			+ String.valueOf(strGuessedValue));
		if (String.valueOf(strGuessedValue).contains(".")) {
		    replaceElement(pic, player);
		} else {
		    pic.printImage();
		    System.out.println(player.getName() + " player Won!!");
		}
	    }

	} else {
	    System.out.println("Wrong guess");
	}

    }

    /**
     * For each correct guess this method is called. It gets the element from
     * vector and replaces it with new element. New Element has some percent of
     * number of characters revealed into it.
     *
     * @param pic    instance of picture class associated with that word
     * @param player player whose turn is going on
     */
    private static void replaceElement(Picture pic, Players player) {
	int numberOfLines = pic.getNumofLines();
	int printNumChars = pic.getNumofCharsToPrint();
	int lenofline = pic.getLenofalineInFile();
	for (int k = 0; k < numberOfLines; k++) {
	    String strToPutInVector2;
	    String strfromVector = pic.getElementOfVector(k);
	    if (player instanceof Player1) {
		strToPutInVector2 = vFile1ImageinTurns.get(k);
	    } else {
		strToPutInVector2 = vFile2ImageinTurns.get(k);
	    }
	    strToPutInVector2 = replaceStringWithMoreChars(printNumChars,
		    lenofline, strfromVector, strToPutInVector2);

	    if (player instanceof Player1) {
		vFile1ImageinTurns.set(k, strToPutInVector2);
	    } else {
		vFile2ImageinTurns.set(k, strToPutInVector2);
	    }

	    // used to print image in each turn
	    System.out.println(strToPutInVector2);
	}
	System.out.println("\n");
    }

    /**
     * For those many number of chars to be shown, each time it finds a random
     * number and replaces that random num index with char from file which is
     * present at that random number index in file.
     *
     * @param printNumChars     Number of more characters that should be shown
     *                          everytime
     * @param lenofline         Length of a line in a file
     * @param strfromVector1    Element of vector1 containing that needs to be
     *                          replaced
     * @param strToPutInVector2 New string with more revealed characters
     * @return The new string
     */
    private static String replaceStringWithMoreChars(int printNumChars,
	    int lenofline, String strfromVector1, String strToPutInVector2) {
	Random rand = new Random();
	while (printNumChars > 0) {
	    // generates random number from 0 to length of line
	    int randomIndex = rand.nextInt(lenofline);
	    while (strToPutInVector2.charAt(randomIndex) != '.') {
		randomIndex = rand.nextInt(lenofline);
	    }

	    strToPutInVector2 = strToPutInVector2.substring(0, randomIndex)
		    + strfromVector1.charAt(randomIndex)
		    + strToPutInVector2.substring(randomIndex + 1);
	    printNumChars--;
	}
	return strToPutInVector2;
    }

}
