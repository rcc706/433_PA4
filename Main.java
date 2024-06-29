// Imports
import java.util.Scanner;

public class Main {

    // Arguments: 1) Number of Chips. 2) Upper bound of range. 3) Program game mode
    public static int chips = 0;
    public static int upperNum = 0;
    public static int gameMode = 0;

    // Tables: 1) Costs. 2) Guesses
    public static int[][] costs;
    public static int[][] guesses;

    public static void main(String[] args) {
        // Checking for valid input:
            // 1. The number of chips
            // 2. The upper bound of the targets' range
            // 3. The game mode (0=min questions, 3=play game with user)
        if (args.length == 3) {
            chips = Integer.parseInt(args[0]);
            upperNum = Integer.parseInt(args[1]);
            gameMode = Integer.parseInt(args[2]);

            // Checking if the gameMode argument is invalid or the upperBound number is < 1
            if (!(gameMode == 0 || gameMode == 3) || !(upperNum >= 1) || (chips < 0))
                invalidInput();
        } else {
            invalidInput();
        }

        // Input is valid and ready to proceed
        System.out.println("Russell Coon");
        initTables();       // Initializes the tables
        getMinQs();         // Finding the minimum number of questions in the worst case

        if (gameMode == 3)
            playerGame();       // Player as referee vs. computer

        // Printing the minimum questions for a target number (only if gameMode = 0 or 3)
        if (gameMode == 0 || gameMode == 3) {
            System.out.println("Chips: " + chips + "\tupperNum: " + upperNum);
            System.out.println("\nFor any target number between 0 and " +
                    upperNum + " with " + chips + " chips, it takes at most " + costs[chips][upperNum] +
                    " questions to identify the target number in the worst case.");
        }
    } // main

    public static void initTables() {
        costs = new int[chips+1][upperNum+1];
        guesses = new int[chips+1][upperNum+1];

        // Setting base cases for the tables (Used to set up the bottom-up dynamic programming)
            // 1st row of the costs table is 1 to upperNum
            // 1st column of the costs table are all 1s
            // 1st row and column of the guesses table are all 1s
        for (int i = 1; i <= upperNum; i++) {
            costs[1][i] = i;
            guesses[1][i] = 1;
        }
        for (int j = 1; j <= chips; j++) {
            costs[j][1] = 1;
            guesses[j][1] = 1;
        }
    } // initTables

    public static void getMinQs() {
        // Loops through each (non-base-case) cells being costs[2][2] to costs[chips][upperBound]
        for(int chp = 2; chp <= chips; chp++) {
            for(int num = 2; num <= upperNum; num++) {
                costs[chp][num] = upperNum + 1;

                for(int count = 1; count <= num; count++) {

                    // Getting the previous cost
                    int prevCost = costs[chp - 1][count - 1];

                    // Is the optimal target < Element (Ak) at index count-1 ---> Yes
                    if (costs[chp][num - count] < prevCost) {
                        prevCost = costs[chp - 1][count - 1] + 1;
                    }

                    // else (is the optimal target < Element (Ak) at index count-1) --> No
                    else {
                        prevCost = costs[chp][num - count] + 1;
                    }

                    // if the current cost > previousCost --> replace with smaller cost --> set guesses for current position
                    if (costs[chp][num] > prevCost) {
                        costs[chp][num] = prevCost;
                        guesses[chp][num] = count;
                    }
                }
            }
        }
    } // getMinQs

    public static void playerGame() {
        // Setting variables: 1) #ofchips. 2) upperNumber. 3) the guess. 4) counter for guess value. 5) current question
        Scanner userInput = new Scanner(System.in);
        int playerChips = chips;
        int upperBound = upperNum;
        int guess = guesses[playerChips][upperBound];
        int lowerBound = 0, question = 1;

        System.out.println("Pick an int between 0 and " + upperNum);

        // Need to run at least once (before guess gets decremented by answer=yes condition)
        do {
            System.out.printf("Chips Left = %d. Question %d: Is target < %d? Answer=y/n --> ", playerChips, question++, lowerBound + guess);

            // Handles user input
            String yesORno = String.valueOf(userInput.next().charAt(0));

            // If the answer is no --> lowerBound increases, upperBound decrease by guess amount
            boolean userAnsYes = yesORno.equalsIgnoreCase("y");
            if (!userAnsYes) {
                upperBound -= guess;
                lowerBound += guess;
            }

            // else the answer is yes --> lowerBound doesn't change, but upperBound decreases by guess-1 amount
            else {
                playerChips -= 1;
                upperBound = guess - 1;
            }

            // Setting the guess value with the new upperBound
            guess = guesses[playerChips][upperBound];

            // Can't run when the number of guesses is 0
        } while(guess != 0);

        // The target was guessed. Printing out the value
        System.out.println("I guessed it. Target = " + lowerBound);
    } // playerGame

    public static void invalidInput() {
        // Prints valid inputs and options for the program.
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("\nCORRECT OPTIONS WHEN RUNNING PROGRAM:");
        System.out.println("Arguments: chips upperBound gameMode");
        System.out.println("\t\tchips      --> \t\t Number of chips. Must be >= 0.");
        System.out.println("\t\tupperBound --> \t\t Upper bound for targets' range. Must be > 0.");
        System.out.println("\t\tgameMode   --> \t\t Program game mode:");
        System.out.println("\t\t\t\t0 = Program shows the minimum number of questions.");
        System.out.println("\t\t\t\t3 = Play game with program referee vs. user\n");
        System.out.println("------------------------------------------------------------------------------------");

        // Exits the program
        System.exit(0);
    } // invalidInput
} // Main