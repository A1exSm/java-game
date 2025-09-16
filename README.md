# Java Game

A Java game built on City University of London's Game Engine.

## Overview

This project is a 2D Java game developed in Java, using a custom game engine provided by City University of London. The game focuses on interactive player movement, level-based progression, animated sprites, and a modular game architecture.

## Key Development Focuses
I Focused on making the game modular and easily expandable, meaning most game features only require an inheritance and a small amount of input to work, and can be build ontop or overriden.

<h3>Commits from GamePlayground repo</h3>
<details>
  <summary>30/01/2025 - <a href="https://github.com/A1exSm/GamePlayground/commit/d55320c243c27dc3dc19cf69b1122e42e800d7db" target="_blank">Initial Commit</a></summary>
  <p>No details (check other ones)</p>
</details>

<details>
  <summary>31/01/2025 - <a href="https://github.com/A1exSm/GamePlayground/commit/366571c3cd416b61fb4a0390a0786c9cc68f42b9" target="_blank">Polygons & Fixtures</a></summary>
  <p>
  + polygon<br>
  + player fixtures<br>
  / altered input override functions<br>
  + population function<br>
  + various changes
</p>
  
</details>
<details>
  <summary>02/02/2025 - <a href="https://github.com/A1exSm/GamePlayground/commit/aa8f0939cc793c3a044bb1089ac078ce9c2bd9bd" target="_blank">Reformat</a></summary>
  <p>
    / removed temp movement functions, re-introduced my one.
  </p>
</details>

<details>
  <summary>03/02/2025 - <a href="https://github.com/A1exSm/GamePlayground/commit/8df1ae49c820b2c5f6424f7d887d0e844164d8ab" target="_blank">Images, View & JFrame</a></summary>
  <p>
    / GameView class to handle UserView setup instead of viewSetup() method<br>
    / GameFrame class to handle JFrame setup instead of setupJFrame() method<br>
    - removed setupJFrame() & viewSetup() along with setup method section due to above two changes.<br>
    + background image<br>
    + player image<br>
    / images are not mine :)
  </p>
</details>

<details>
  <summary>03/02/2025 - <a href="https://github.com/A1exSm/GamePlayground/commit/b370221fa2db782d79be3fd773d372106135c9ea" target="_blank">Sprites, Actions & Menu</a></summary>
  <p>
    + Player class to extent walker, added left and right icon support & implementation.<br>
    + trampoline class<br>
    + timer for game time<br>
    / potential asset repository: https://kenney.nl<br>
    + open source gif sprites from various sources, converted from sprite sheets.<br>
    + game menu class to handle settings, only present setting as of right now is pause with a shortcut of ctrl + p<br>
    + game.animation direction and file based of player's velocity, if the player has a non-zero y-velocity the player game.animation falls/jumps accordingly to the x-direction. This is using stepListener.<br>
    + game remembers what position the player is facing and sets idle game.animation in that direction.<br>
    + timer to stop the attack gif 0.8s after execution<br>
    / gifs seem to be cached in memory and un-paused (I think this is the issue) causing gifs to be re-used, however my gifs only run once (an attempt at preventing some bugs) thus they can't be executed again, maybe using a separate declaration everytime will fix this (hopefully future me will remember this)<br>
    / FYI I used ezgif.com sprite sheet splitter to make the gifs, allows manipulation of frames, speed, colour etc... very useful.<br>
  </p>
</details>

<details>
  <summary>04/02/2025 - <a href="https://github.com/A1exSm/GamePlayground/commit/ed219732ef55b97c83a955565315125ceefcf9f6" target="_blank">Confined Methods to Classes, *moved to new repo*</a></summary>
  <p>No details (check other ones)</p>
</details>
