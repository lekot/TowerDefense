#TOWER DEFENSE
Thijs Daniels & Peter van 't Zand
January 2013

#DESCRIPTION
This is a basic framework for tower defense games, which can easily be extended. Built on the AndEngine, a scene is created with one or more spawn-points, several way-points and several base-points. Spawn-points release waves of enemies into the scene every time their timer hits zero. These enemies will then follow the path set out from the spawn-point that spawned them, until they reach the last waypoint, at which moment the player takes damage. The player's goal is to survive as by not letting too many enemies finish their path. The user can kill enemies by building towers on the base-points, which will attack anything that is in range.

#SOFTWARE
- Android 4.2 (API Level 17)
- Eclipse with Android SDK
- AndEngine

#CLASSES

###Tower
```java
- name
- reach
- speed (delay in ms to wait between firing two rounds)
- sprite
- Round
- price
- onCreate() (infinite loop, on separate thread, of checking whether an enemy is in range and if so, fire(Round, target))
- fire(Round, target) {creates a new Round(target)}
```
	
###Round
```java
- name
- damage
- speed (some factor that determines movement speed)
- splash_radius
- sprite
- target (passed in creator by the tower that fires it)
```

###Enemy
```java
- name
- sprite
- speed (some factor that determines movement speed)
- target (the waypoint the enemy is currently walking towards)
- status (normal, dead, slowed, etc.)
- points (the number of points the player gains from killing it)
- money (the amount of coins the player gains from killing it)
- max health
- current health
```

###SpawnPoint
```java
- waves (array of arrays of enemies)
- current_wave (initializes to 0)
- wave_delay (time in ms to wait between one wave and the next)
- location (array of x,y coordinates)
- path (array of waypoints the spawned enemy will follow)
- onCreate() {setInterval(nextWave(), wave_delay)}
- nextWave() {calls spawn(Enemy) for all enemies, one by one, of the array at the current_wave index of the waves array, then increments current_wave}
- spawn(Enemy) {creates new Enemy(location)}
```

###WayPoint
```java
- name?
- location (array of x,y coordinates)
```

###BasePoint
```java
- location (array of x,y coordinates)
- onTouch() {listener that opens market to allow for buying towers}
```

###TowerDefense (Main Activity Class)
```java
- scene
- score
- time
- waves_left (sum of waves left of all SpawnPoints)
- money
- lifes
- market (array of all possible towers)
- SPAWN_DELAY (constant time in ms for a spawn-point to wait between one spawn and the next)
```

###HighScores
```java
- SQLite DB of high scores
```

#DIVISION

###Thijs
- preliminary sprites
- prototype TowerDefense (i.e. building the scene)

###Peter
- prototype SpawnPoint
- prototype WayPoint
- prototype Enemy