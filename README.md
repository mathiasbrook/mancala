# Mancala

A console version of Mancala in Java where you play against the computer.

## Features
- Standard 6-hole board with 4 stones in each hole and a store (mancala) on each side
- Extra turn when your last stone lands in your own store
- Captures: if your last stone lands in an empty hole on your side, you take the stones in the hole across from it
- The computer picks a random non-empty hole and also gets extra turns and captures
- Checks for invalid input and empty holes
- When one side runs out of stones the game ends, the stones left on each side go into that side's store (standard rules), and the winner (or a tie) is printed

## How to run
```
javac Mancala.java && java Mancala
```
Your holes are the bottom row, numbered 1-6 from left to right. Your store is on the right, the computer's store is on the left.

To run the test moves from class instead of a game:
```
java Mancala test
```

## Sample output
Real output from a game (lines starting with `>` are what I typed):

```
  4 4 4 4 4 4
0             0
  4 4 4 4 4 4
Your turn. Enter a number between 1-6
> 3
Player 3
  4 4 4 4 4 4
0             1
  4 4 0 5 5 5
Your turn. Enter a number between 1-6
> 4
Player 4
  4 4 4 4 5 5
0             2
  4 4 0 0 6 6
Computer 2
  5 5 5 5 0 5
1             2
  4 4 0 0 6 6
Computer 4
  6 6 0 5 0 5
2             2
  5 5 0 0 6 6
Your turn. Enter a number between 1-6
> 2
```
...
```
Player 2
  0 1 0 0 0 0
17             25
  1 0 2 1 1 0
Computer 5
  1 0 0 0 0 0
18             25
  0 0 2 1 1 0
Your turn. Enter a number between 1-6
> 3
Player 3
  1 0 0 0 0 0
18             25
  0 0 0 2 2 0
Computer 6
  0 0 0 0 0 0
19             25
  0 0 0 2 2 0
The Player has won!
  0 0 0 0 0 0
19             29
  0 0 0 0 0 0
```

The computer emptied its side, so the 4 stones left on my side went into my store.

## Concepts used
- Arrays (the whole board is one `int[14]` with the two stores at index 6 and 13)
- Converting between player-friendly hole numbers and array indexes
- Wrapping around an array while distributing stones
- Loops and conditionals for turn logic, captures and game over
- `Scanner` for user input, `Math.random()` for the computer

## What I'd add next
- A smarter computer that looks for captures and extra turns instead of picking randomly
- Two player mode
- Letting you pick the number of starting stones
- Handling non-number input without crashing
