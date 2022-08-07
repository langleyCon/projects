/*
 *
 *
 * CS 441/541: Page Fault Algorithm Simulator (Project 6)
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

/******************************
 * Defines
 ******************************/



/******************************
 * Structures
 ******************************/
 struct Memory{
   int id;
   int bit;
   int eBit;
 };
 typedef struct Memory memory;


/******************************
 * Global Variables
 ******************************/
 char *fileName = NULL;
 char **memoryList;
 int numOfRefrences = 0;
 int frameToShow = 0;//if -1 show all frames


/******************************
 * Function declarations
 ******************************/
char * getCommandLineArguments(int argc, char **argv);
void processFile();
int firstInFirstOut(int numOfFrames);
int checkHit(int *memBlocks, int size, char memToCompare);
int leastRecentlyUsed(int numOfFrames);
int * shuffleList(int * list, int size, char newMem);
int secondChance(int numOfFrames);
int checkHitSecoondC(memory *memBlocks, int size, char memToCompare);
int enhancedSC(int numOfFrames);
int checkHitEnSecoondC(memory *memBlocks, int size, char memToCompare, char rOrW);
