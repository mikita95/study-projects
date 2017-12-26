#ifndef CARDS_H
#define CARDS_H

#include "texas_holdem.h"

enum Suit {
    clubs, diamonds, hearts, spades
};

class Card {
protected:
    Suit suit;
    int rank;
public:
    Card() {}
};

class OpenCard: Card {
public:
    // returns rank of a card
    int getRank()const;
    // returns Suit of a card
    Suit getSuit()const;
    OpenCard();
    OpenCard(const Suit, const int);
    bool operator==(OpenCard const&);
    // returns description of a card
    std::string toString() const;
};

class CloseCard: Card {
public:
    friend class TexasHoldem;
    CloseCard();
    CloseCard(const Suit, const int);
};

#endif
