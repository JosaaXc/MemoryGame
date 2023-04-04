package com.example.mymemory.models

import com.example.mymemory.utils.DEAFULT_ICONS

class MemoryGame (private  val boardSize: BoardSize){

    val cards: List<MemoryCard>
    var numPairsFound = 0

    private var numCardFlips = 0

    private var indexOfSingSelectedCard: Int? = null

    init{
        val chosenImages = DEAFULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val ramdomizedImages = (chosenImages + chosenImages).shuffled()
        cards = ramdomizedImages.map { MemoryCard(it) }
    }
    fun flipCard(position: Int): Boolean {
        numCardFlips++
        val card = cards[position]
        //Three cases
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over = > restore cards + flip over the selected card
        var foundMatch = false
        if(indexOfSingSelectedCard == null){
            // 0 or 2 cards previously flipped over
            restoreCards()
            indexOfSingSelectedCard = position
        }else{
            //exactly one card previously flipped over
            foundMatch = checkForMatch(indexOfSingSelectedCard!!, position)
            indexOfSingSelectedCard = null
        }
        card.isFaceUp= !card.isFaceUp
                return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if(cards[position1].identifier != cards[position2].identifier)
            return false
        cards[position1].isMatched= true
        cards[position2].isMatched= true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for(card in cards){
            if(!card.isMatched){
                card.isFaceUp = false
            }
        }
    }

    fun haveWongame(): Boolean {
        return numPairsFound ==boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }

    fun getNumMoves(): Int {
        return numCardFlips / 2
    }
}