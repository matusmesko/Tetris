# Tetris Game

Welcome to the **Tetris Game**! This project is a classic implementation of the famous block-stacking game, featuring a sleek UI built with **Java Swing** and efficient game mechanics.

<p align="center">
    <img src="https://i.imgur.com/fRMhyZZ.png" alt="preview">
</p>
## Features

- **Classic Tetris gameplay:** Rotate, move, and drop tetrominoes to clear lines.
- **Next Tetromino Preview:** See the upcoming piece to plan your moves.
- **Game Over Screen:** Displays final score and level with options to restart.
- **Main Menu:** Play the game or access settings.
- **Tetris Panel:** Tetris game
- **Settings Panel:** Toggle background music and return to the main menu.
- **Keyboard Controls:**
    - `W / ↑` - Rotate Tetromino
    - `A / ←` - Move Left
    - `D / →` - Move Right
    - `S / ↓` - Move Down
    - `Space` - Drop Tetromino to the bottom
    - `ESC / P` - Pause the game
    - `R` - Restart the game

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/matusmesko/Tetris.git
   cd tetris-game
   ```
2. Compile the project using **Maven** or your preferred Java IDE.
3. Run the application:
   ```bash
   java -jar tetris-game.jar
   ```

## Project Structure

```
|-- src
|   |-- matus
|   |   |-- MainWindow.java
|   |   |-- Utils.java
|   |   |-- Main.java
|   |   |-- MusicController.java
|   |   |-- Tetromino.java
|   |   |-- TetrominoType.java
|   |-- matus/panels
|   |   |-- NextTetrominoPanel.java
|   |   |-- MainMenuPanel.java
|   |   |-- GameOverPanel.java
|   |   |-- TetrisPanel.java
|   |   |-- SettingsPanel.java
|-- resources
|   |-- tetris-logo.png
|   |-- background-music.wav
|   |-- on.wav
|   |-- off.wav
|-- README.md
|-- pom.xml
|-- tetris-game.jar
```

## How to Play

1. Click **Play** on the main menu to start the game.
2. Use the keyboard controls to maneuver the falling blocks.
3. Fill rows completely to clear them and score points.
4. Aim for the highest score and level up!

## Customization

You can customize the following aspects of the game:

- **Colors:** Modify the `Utils.getColorTable()` method.
- **Music:** Replace the `background-music.wav` file in the resources folder.
- **UI Layout:** Adjust `JPanel` settings in the respective panel classes.


## License

This project is licensed under the MIT License. Feel free to modify and distribute it as you wish.

## Contact

If you have any questions or suggestions, feel free to reach out!

**Email:** meskomatusko@gmail.com  
**GitHub:** [matusmesko](https://github.com/matusmesko)

