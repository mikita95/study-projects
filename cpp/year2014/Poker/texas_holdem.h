#ifndef TEXAS_HOLDEM_H
#define TEXAS_HOLDEM_H

#include <vector>
#include "cards.h"
#include "player.h"
#include "table.h"
#include "gtest/gtest.h"
#include "memory.h"

class OpenCard;

class CloseCard;

class Player;

class Table;

class TexasHoldem {
private:
    struct PlayerData {
        uint32_t cash;
        OpenCard *hand;
        uint32_t currBet;
        Player *p;


        ~PlayerData();
    } *players;

    uint32_t playersCount, bank, littleBlind, dealer;

    void show(Player *p, OpenCard *cards, uint32_t *value);

    std::string rankToString(uint32_t in);

    // returns {combitation_id, combination_relevance, 1st_kicker, 2nd_kicker, 3rd_kicker, 4th_kicker, 5th_kicker}
    uint32_t *enter(OpenCard *cards);

    // returns pointer to 5-elements array with combination with highest ranks
    std::pair<OpenCard *, uint32_t *> strongestCombination(OpenCard *);

    // returns vector with winnerses ids
    std::vector<uint32_t> show();

    // player bets starting from the next to big blind
    void doBets(uint32_t pls);

    // play round with players[i] (i in live)
    void doRound();

    // give new cards to players & deck
    void shuffle();

    // return ids of players with cash >= littleBlind * 2 (big blind)
    std::vector<uint32_t> refresh();

    // remove card from deck and add to table
    void openNext();

    // returns number of players which are able to bet
    size_t inGameWithCash();

    // returns state for players[player].p
    Table getTable(uint32_t player, std::vector<uint32_t> live);

    std::vector<OpenCard> table;
    std::vector<CloseCard> deck;

    // ids of players in game
    std::vector<uint32_t> live;

    // create new player from templates & add to instance
    template<typename First>
    void addPlayers(uint32_t cnt, First *first);

    template<typename First, typename... Rest>
    void addPlayers(uint32_t cnt, First *first, Rest *... rest);

    TexasHoldem();

    // static no-class function permits to create object without template in it
    // should be used instead of default constructor
    template<typename ... Players>
    friend TexasHoldem *makeGame(uint32_t n, uint32_t startCash, uint32_t littleBlind,
            Players *... p);


    FRIEND_TEST(correctness, evaluateCombination);

    FRIEND_TEST(correctness, pickCombination);

    FRIEND_TEST(correctness, shuffleTest);

public:
    ~TexasHoldem();

    // running until somebody wins
    void run();
};

#endif
