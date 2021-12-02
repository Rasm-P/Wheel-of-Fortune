package com.example.wheeloffortune.data

import com.example.wheeloffortune.models.Category

class CategoryData() {

    //Loads in Category data used in the app
    fun loadCategories(): List<Category> {
        return listOf(
            Category("Bird", listOf("Mute swan","Bald eagle","Woodpecker")),
            Category("Person", listOf("Sean connery","Donald Trump","Boris Johnson")),
            Category("Landmark", listOf("Pyramids of Giza","Big Ben","The Little Mermaid")),
            Category("Around the house", listOf("Cheese grater","Washing machine","Vacuum cleaner")),
            Category("Fictional character", listOf("Indiana Jones","Luke Skywalker","James Bond")),
            Category("Countries", listOf("Bosnien Hercegovina","The United States","Denmark")),
            Category("Slogan", listOf("Just Do It","Think Different","Open Happiness")),
            Category("Song", listOf("Bohemian Rhapsody","Billie Jean","Seven Nation Army")),
            Category("Board games", listOf("Stratego","Monopoly","Battleship")),
            Category("Movies", listOf("The terminator","Casino Royale","Alice in Wonderland")),
        )
    }
}