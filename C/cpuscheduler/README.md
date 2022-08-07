# CS441/541 Project 2

## Author(s):

Connor Langley


## Date:

2/15/2018


## Description:

This program simulates a cpu scheduler


## How to build the software

To compile:
      1) Go to the location of the scheduler.c file
      2) Type make into the command line
      The program scheduler should now be compiled


## How to use the software

How to use:
        1) Go to the location of the program scheduler.
        2) Type ./scheduler into the command line followed by the list of commands you
            wish to use.
Accepted Commands:
        -s #  This command is REQUIRED and tells the program which scheduling algorithm
              to use. # is a int specifying which algorithm to use.
              Accepted #: 1 - For first come first out algorithm
                          2 - For short job first algorithm
                          3 - For priority algorithm
                          4 - For round robin algorithm

        -q #   This command is optional and is used to specify the quantum in round
               robin. # can be any positive integer.

        [Your test file]  This is REQUIRED. You need to specify a given test file to run
                          the program. Do not include the [] when specifying the file
                          location.
        !THESE COMMANDS CAN COME IN ANY ORDER!

Examples:
          ./scheduler -s 1 test.txt
          ./scheduler -s 2 test.txt -q 4 (note any q specified will not affect non RR)
          ./scheduler -q 4 test.txt -s 4
          ./scheduler -s 4 test.txt -q 4


## How the software was tested

The program was tested using the given tests and 5 of my own tests.

## Test Suite

Given tests: given-tests
My tests: tests


## Known bugs and problem areas

Round Robin does not seem to work correctly.
Tests are failing for SJF and FCFO when the given processes is over a certain number
For whatever reason the priority tests begin to fail at level 6. They start to produce the wrong output at the second or third process with level 5 priority.
