cmake_minimum_required(VERSION 2.8)
project(GRAPH)
include_directories(${GRAPH_SOURCE_DIR})

add_executable(graph_testing
               graph_testing.cpp
               graph.cpp
               graph.h
               gtest/gtest-all.cc
               gtest/gtest.h
               gtest/gtest_main.cc)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -std=gnu++0x -pedantic")

target_link_libraries(graph_testing -lzdll)
