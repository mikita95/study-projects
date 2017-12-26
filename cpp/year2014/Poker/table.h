#ifndef TABLE_H
#define TABLE_H

#include "cards.h"

class OpenCard;

class Table {
public:
    uint32_t enemyCount, bank, littleBlind, playerCash, playerBet;
    OpenCard *hand;
    std::vector <OpenCard> board;
    std::vector <std::string> names;
    std::vector <uint32_t> cash, bets;
    Table();
    ~Table();
    void print() const;
};

#endif
