#ifndef PLAYER_H
#define PLAYER_H

#include <string>

#include "texas_holdem.h"
#include "table.h"

class Table;

class Player {
private:
    std::string name;
public:
    Player() {}
    Player(std::string n) { name = n; }
    // returns name of player
    std::string getName() const { return name; }
    virtual ~Player() {}
    // returns bet of player or 0 if player checks
    virtual uint32_t bet(uint32_t minBet, Table state) = 0;
};

#endif
