#ifndef HUMAN_PLAYER_H
#define HUMAN_PLAYER_H

#include "player.h"
#include "cards.h"

class HumanPlayer: public Player {
private:
public:
    HumanPlayer() {}
    HumanPlayer(std::string name): Player(name) { }
    ~HumanPlayer() {}
    // ask user to make bet
    uint32_t bet(uint32_t sum, Table state);
};

#endif
