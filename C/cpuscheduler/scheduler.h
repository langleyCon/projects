/*
 *
 *
 * CS 441/541: CPU Scheduler (Project 1)
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
 struct process{
   int proNum;
   int cpuBurst;
   int proPriotity;
   int wait;
   int turn;
   int timesStopped;
 };
 typedef struct process process;

/******************************
 * Global Variables
 ******************************/
 int totalNumProcess = 0;

/******************************
 * Function declarations
 ******************************/
 int checkArgs(int numIdx, char **args);
 void processCmd(int s, int q, char *file);
 void firstCfirstO(int q, char *file);
 void shortJobF(int q, char *file);
 void priority(int q, char *file);
 void roundR(int q, char *file);
 process* processFile(char *file);
 process createProcess(int id, int burst, int pr, process *pro);
 void printArrivalOrder(process *pro);
 void printProcessInfo(process *pro);
 void printRunResults(process *pro);
