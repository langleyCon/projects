# CS441/541 Project 6

## Author(s):

Connor Langley


## Date:

4/18/2018


## Description:

Page Fault Algorithm Simulator


## How to build the software

1) Go to file location of pagefault.c
2) type 'make' into the command line.


## How to use the software

1)go to file location of pagefault
2)type './pagefault' followed by a file to be read.
3)(optional) type '-f #' to specify a number of frames to be used.
              -# is an integer between 1 and 7

Example:
  ./pagefault test.txt
  ./pagefault test.txt -f 4
  ./pagefault -f 4 test.txt


## How the software was tested

The Software was tested using the given tests level 1-7

## Test Suite




## Known bugs and problem areas

The Enhanced second chance algorithm no longer creates an infinite loop.

The optimal algorithm is not implamented.

The formatting for the output is slightly off.
