#include <fstream>
#include <time.h>
#include <stdlib.h>

#include "gtest/gtest.h"
#include "texas_holdem.h"
#include "human_player.h"
#include <algorithm>

template <typename... Players>
TexasHoldem* makeGame(uint32_t n, uint32_t startCash, uint32_t littleBlind, Players *... p) {
    TexasHoldem* result = new TexasHoldem();
    result->playersCount = n;
    result->littleBlind = littleBlind;
    result->deck = std::vector <CloseCard>();
    result->table = std::vector <OpenCard>();

    result->players = new TexasHoldem::PlayerData[result->playersCount];
    result->addPlayers(0, p...);

    for (unsigned i = 0; i < result->playersCount; i++) {
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

TEST(correctness, constructor) {
    TexasHoldem* game = makeGame((unsigned) 2, (unsigned) 100, (unsigned) 10,
            new HumanPlayer("First player"), new HumanPlayer("Second player"));
    delete game;
}

TEST(correctness, shuffleTest) {
    TexasHoldem* game = makeGame((unsigned) 2, (unsigned) 100, (unsigned) 10,
            new HumanPlayer("First player"), new HumanPlayer("Second player"));
    OpenCard cards[9];
    for (int i = 0; i < 1000; i++) {
        game->shuffle();
        for (int j = 0; j < 5; j++) {
            game->openNext();
            cards[j] = game->table.back();
        }
        cards[5] = game->players[0].hand[0];
        cards[6] = game->players[0].hand[1];
        cards[7] = game->players[1].hand[0];
        cards[8] = game->players[1].hand[1];

        for (int j = 0; j < 9; j++) {
            EXPECT_TRUE(cards[j].getRank() >= 2);
            EXPECT_TRUE(cards[j].getRank() <= 14);
            EXPECT_TRUE((int)cards[j].getSuit() <= 4);
            EXPECT_EQ(1, std::count(cards, cards + 9, cards[j]));
        }
    }
    delete game;
}


