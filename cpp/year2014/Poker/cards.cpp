#include "cards.h"

OpenCard::OpenCard() {
    suit = Suit::clubs;
    rank = 0;
}

OpenCard::OpenCard(Suit const x, int const y) {
    suit = x;
    rank = y;
}

int OpenCard::getRank()const {
    return rank;
}

Suit OpenCard::getSuit()const {
    return suit;
}

CloseCard::CloseCard() {}

CloseCard::CloseCard(Suit const x, int const y) {
    suit = x;
    rank = y;
}

bool OpenCard::operator==(OpenCard const &another) {
    return rank == another.getRank() && suit == another.getSuit();
}

std::string OpenCard::toString() const {
    std::string rankStr;
    switch (rank) {
        case 14:
            rankStr = "Ace";
            break;
        case 13:
            rankStr = "King";
            break;
        case 12:
            rankStr = "Queen";
            break;
        case 11:
            rankStr = "Jack";
            break;
        default:
            rankStr = std::to_string(rank);
            break;
    }
    std::string suitChars[] = {" clubs", " diamonds", " hearts", " spades"};
    return (rankStr + suitChars[(int) suit]);
}
