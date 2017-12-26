#include "table.h"
#include <iostream>
#include "cards.h"

Table::Table() {
    hand = std::nullptr_t();
}

Table::~Table() {
    if (hand != std::nullptr_t())
        delete[] hand;
}

void Table::print() const {
    std::cout << "Current pot: " << bank << "\n";
    std::cout << "Your cash: " << playerCash << "; total bet: " << playerBet << "\n";
    for (uint32_t i = 0; i < enemyCount; i++) {
        std::cout << names[i] << "'s cash: " << cash[i] << "; total bet: " << bets[i] << "\n";
    }
    if (board.size() != 0) {
        std::cout << "Board cards: ";
        for (uint32_t i = 0; i < board.size(); i++) {
            std::cout << board[i].toString() << " ";
        }
        std::cout << "\n";
    }
    std::cout << "Your cards: " << hand[0].toString() << " " << hand[1].toString() << "\n";
}
