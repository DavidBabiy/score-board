# Score board
#### Library for tracking teams score.

## How to run

### Requirements
- Java 17
- Pre-installed Maven

### How to run tests
1. Open Terminal
2. Go to root of the project
3. Run command `mvn clean test`


## Considerations

### 1. Update score
In my opinion, "Update score" - should literally UPDATE the score with passed parameters, for example:
- Score is `1 - 0`
- Call `updateScore(0, 1)`
- Now score is `0 - 1`

That leads me to question. What are the restrictions?
Natural restriction is that the score **cannot** be `negative`.
But can the score be updated in the way described above? What are the cases when team can lose point, maybe when arbiter decides it is `invalid goal`.

I assume, `incrementScore` approach would be much more safe, because the logic would be encapsulated.
But additional functionality needed for handling edge cases such as `invalid goal`.

### 2. General behaviour
In my mind, it makes sense to require calling `finishGame()` before starting a NEW one and consider that as a trigger to `store game's summary`.
Also, before starting NEW game - ongoing game should be finished.

### 3. Data types
#### a. Score
I have never seen the score which can exceed maximum value of `byte`, and to my mind its capacity is more than enough for football.
But I used primitive `int` for the sake of convenience. Although, if this program would be combined with ORM, I`d rather use wrapper.

#### b. Games "database"
`getGamesSummary` should return sorted element collection, so we need to decide how to store that data:
- (✓) `TreeSet` - brings us `O(log(n))` complexity on READ, and instead we receive out of the box sorting. For READ, we need the whole tree, so it is linear complexity.
- (?) `ArrayList` - list is a great option for writing data by `O(1)`. And probably better would be to sort it on demand (on READ).

In order to choose, we need to know what will be used frequently READ or WRITE. I am not acknowledged about it, 
so I chose `TreeSet`, because it seemed to be more balanced.

### 4. Concurrency
First, I considered to make app concurrency-safe for WRITE, but as for me, football score tracking is something consequent and I couldn't imagine case where multithreading is useful here.