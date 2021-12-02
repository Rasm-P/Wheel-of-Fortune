# Wheel-of-Fortune #
App for the Wheel of Fortune game
Created by student: S215777

## Requirements ##
*The game rules listed should be implemented.
*The game should be able to be played again when finished.
*The application should have a single activity and use fragments.
*The application should use the Navigation Component.
*Android architecture guidelines should be followed.
*There should be at least three screens: e.g. word guessing, game won, game lost.
*A recyclerview should be implemented to display e.g a list.
*Version control (GitHub or GitLab) should be used. (Access should be given to Ian and the teaching assistants - usernames will be provided later.)
*The app name should start with the student number. (Edit the values/strings.xml e.g. <string name="app_name">s123456 Lykkehjulet</string>)
*The minSdkVersion should be 24
*The application must be implemented in Kotlin

## Game rules implemented ##
*A phrase is randomly chosen and displayed along with a category
*The phrase is displayed with the letters hidden, and you have to reveal them by choosing letters
*Points are incremented by the value shown times the number of occurrences of the letter
*If the letter is not present you lose a ”life”
*In the event of “extra turn” being shown, you are given an extra life
*In the event of “miss turn” being shown, you lose a life and do not get to choose a letter
*In the event of “bankrupt” being shown, you lose all your points
*The game is lost if you have no more lives, and won if you reveal all the words

## Documentation ##
All game rules from the initial assignment are implemented in the application.
The application uses a single activity with fragments for WordGuessing, GameWon and GameLost.
The application implements the Model View ViewModel (MVVM) architecture.
A recycler view is implemented for showing each word in the hidden phrase, as per the early assignment requirements.
The application uses a navigation component and nav graph for page navigation.
The minSdkVersion is set to 24 as required.
As of now data is predefined in the code itself and loaded directly into the game.
Due to problems in making the layout fit on devices with smaller screen, a few changes in scaling down layout components was made.

### Given more time ###
Data could be fetched from a JSON file instead of being predefined in the code itself.
To do this however one would need access to the view context in the ViewModel. 
A way to bypass this would be by giving it a constructor and parsing in the context as a parameter.
This would however make for an awkward implementation, and probably need some more thought.

More animations for winning and losing the game would also have been a great addition, 
but had to be left out due to the time spent implementing the wheel rotation animation.

### Material ### 
Initial setup of game logic and ViewModel has been inspired by the developer app Unscramble:
https://developer.android.com/courses/pathways/android-basics-kotlin-unit-3-pathway-3

A visual spinning wheel with rotate animation has been implemented inspired by this example:
https://youtu.be/5O2Uox-TR00