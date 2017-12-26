#include <iostream>
#include "table.h"
#include "texas_holdem.h"
#include "human_player.h"
#include "cards.h"

template <typename... Players>
TexasHoldem* makeGame(uint32_t n, uint32_t startCash, uint32_t littleBlind, Players *... p) {
    TexasHoldem* result = new TexasHoldem();
    result->playersCount = n;
    result->littleBlind = littleBlind;
    result->deck = std::vector <CloseCard>();
    result->table = std::vector <OpenCard>();

    result->players = new TexasHoldem::PlayerData[result->playersCount];
    result->addPlayers(0, p...);

    for (uint32_t i = 0; i < result->playersCount; i++) {
        result->players[i].cash = startCash;
        result->players[i].hand = new OpenCard[2];
    }
    result->shuffle();

    return result;
}

template <typename Player>
void TexasHoldem::addPlayers(unsigned cnt, Player* p) {
    if (cnt < playersCount) {
        players[cnt].p = p;
    }
}

template <typename Player, typename... Team>
void TexasHoldem::addPlayers(unsigned cnt, Player* p, Team* ... t) {
    if (cnt < playersCount) {
        players[cnt].p = p;
        addPlayers(++cnt, t...);
    }
}

using namespace std;

int main() {
    TexasHoldem* game = makeGame((uint32_t) 2, (uint32_t) 100, (uint32_t) 10, new HumanPlayer("First player"), new HumanPlayer("Second player"));
    game->run();
    delete game;
    return 0;
}
