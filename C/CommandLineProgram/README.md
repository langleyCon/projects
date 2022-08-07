# CS441/541 C Lab - Part 1
## Program 2 - cmdline

## Author(s):

Connor Langley


## Date:

2/5/2018


## Description:

This Program takes in command line prompts and prints out the results.


## How to build the software

First go to the file location of 2-cmdline folder e.g cd documents/cs441-s18-project-1/2-cmdline
Then to compile type "make" in the command line.


## How to use the software

To use the program start by typing ./cmdline on the command line followed by the commands you wish to run.

List of Commands:

Required:
          -n "x"
            - x is a positive integer greater than 0. Exclude the "".
Optional:
          -d
            - "-d" command enables debugging. Debugging is disabled by default.
          -stage "str"
            - Use "-stage" to specify a stage. "str" is a string of the specified stage.
            - All stages are ran by default if this command is not present.
Examples:
          - ./cmdline -n 42
          - ./cmdline -d -n 34
          - ./cmdline -n 43 -d -stage FOO
          - ./cmdline -n 44 -stage GOO

## How the software was tested

This program was tested using the given tests by running "make check".
It was tested on Ubuntu 16.04, on the class server, and on AutoLab.


## Known bugs and problem areas

If multiple stages are specified the program will not run. The program will not recognize the second stage specified.
e.g ./cmdline -n 42 -stage FOO GOO
      - will produce : Error: Unknown option : GOO
