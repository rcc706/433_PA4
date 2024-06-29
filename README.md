# CSCI 433 Programming Assignment 4 - A Number Guessing Game

The goal of this project was to implement an algorithm that would compute the number of minimum
questions needed to guess a target between a range of numbers in the worst case. The arguments were: the
number of chips, the amount of numbers (0 to upper bound), and the gamemode. My implementaiton of
this algorithm include a bottom-up dynamic programming technique for finding all the costs and guesses
to compute the minimum number of questions. It also includes an interactive guessing game between the
computer and the user (referee) in which the user knows the target number and the algorithm guesses it.
My implementation for the program is outlined below:


## Program Implementation
1. Check Valid input:
  a. 3 arguments
  b. Gamode must be 0 (minimum questions) or 3 (player vs program)
  c. The upperBound must be greater than or equal to 1.
  d. The number of chips must be greater than 0.
2. If there is invalid input, there will be a message printed regarding how to enter valid arguments.
3. The cost and guess tables are intialized (base-cases) for the bottom-up dynamic programming.
4. The minimum questions in worst case (using dynamic programming) will find the cost for the
position in the cost table (described more in part 2).
5. If the gamemode option is 3, the playerGame() function will then be called to use the tables from
steps 3 and 4 to run the interactive game with the user.
