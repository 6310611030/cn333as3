package com.example.gameapp

const val MAX_NO_OF_QUIZZES = 10
const val SCORE_INCREASE = 1

// Set with all the words for the Game
val allQuizzes: Set<List<String>> =
    setOf(
        listOf("In which Italian city can you find the Colosseum?", "Rome", "Venice", "Naples", "Milan"),
        listOf("What is the largest canyon in the world?", "Grand Canyon, USA", "Verdon Gorge, France", "King’s Canyon, Australia", "Fjaðrárgljúfur Canyon, Iceland"),
        listOf("How long is the border between the United States and Canada?", "5,525 miles", "3,525 miles", "4,525 miles", "6,525 miles"),
        listOf("What is the largest active volcano in the world?", "Mouna Loa", "Mount Etna", "Mount Vesuvius", "Mount Batur"),
        listOf("In which museum can you find Leonardo Da Vinci’s Mona Lisa?", "Le Louvre", "Uffizi Museum", "British Museum", "Metropolitan Museum of Art"),
        listOf("What is the largest continent in size?", "Asia", "Africa", "Europe", "North America"),
        listOf("Which famous inventor invented the telephone?", "Alexander Graham Bell", "Thomas Edison", "Benjamin Franklin", "Nikola Tesla"),
        listOf("If you are born on the 1st of January, which star sign are you?", "Capricorn", "Scorpio", "Libra", "Aries"),
        listOf("Which country is the footballer Cristiano Ronaldo from?", "Portugal", "Spain", "Brazil", "Uruguay"),
        listOf("What is the longest river in the world?", "Nile", "Amazon River", "Yellow River", "Congo River"),
        listOf("How many sides has a Hexagon?", "6", "5", "7", "8"),
        listOf("What does NASA stand for?", "National Aeronautics and Space Administration", "Nautical And Space Association", "National Aeronautics and Space Association", "New Aeronautics and Spacial Administration"),
        listOf("What is the name of the gem in the movie Titanic?", "Heart of the Ocean", "Call of the Ocean", "Heart of Love", "Call of Love"),
        listOf("Who is the CEO of Amazon?", "Jeff Bezos", "Elon Musk", "Tim Cook", "Mark Zuckerberg"),
        listOf("Which band released the song “Wonderwall” in the 90s??", "Oasis", "Joy Division", "The Verge", "Nirvana"),
    )