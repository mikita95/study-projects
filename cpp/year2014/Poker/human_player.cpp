#include <iostream>
#include "human_player.h"

uint32_t HumanPlayer::bet(uint32_t sum, Table state) {
    std::cout << "[" << this->getName() << "] Enter your bet (min is " << sum << ", 0 for check/fold): ";
    std::string s;
    std::getline(std::cin, s);
    return std::stoul(s);
}
