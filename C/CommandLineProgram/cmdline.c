/*
 * Connor Langley
 * 2/1/2018
 *
 * Checks command line arguments
 */
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int checkInput(int num, char **input);
void printResult(int iter, int debugToggle, char *s);

int main(int argc, char **argv) {

    return checkInput(argc, argv);
}

int checkInput(int num, char **input){
  int reqN = 0;
  int iterations;
  int debug = 0;
  char * stage = NULL;

  if(num == 1){
    printf("Error: -n option required to be specified.\n");
    return -1;
  }
  else if(num == 2 && !strcmp(input[1], "-n")){
    printf("Error: Not enough arguments to the -n option.\n");
    return -1;
  }
  else if(num > 2){
    int i;
    for(i = 1; i < num; i++){
      if(!strcmp(input[i], "-n") || !strcmp(input[i], "-d") || !strcmp(input[i], "-stage")){
        if(!strcmp(input[i], "-n") && i+1 < num){
          char *tmp = NULL;
          tmp = strdup(input[i+1]);
          iterations = atoi(tmp);
          if(iterations <= 0){
            printf("Error: -n option requires a positive integer greater than 0\n");
            return -1;
          }
          reqN = 1;
          i++;
        }
        else if(!strcmp(input[i], "-d")){
          debug = 1;
        }
        else if(!strcmp(input[i], "-stage") && i +1 < num){
          if(strcmp(input[i+1], "-n") || strcmp(input[i+1], "-d") || strcmp(input[i+1], "-stage")){
            stage = strdup(input[i+1]);
          }
          else{
            printf("Error: Stage must exculde the following -v -d -stage as these are reserved commands\n");
            return -1;
          }
          i++;
        }
        else if(!strcmp(input[i], "-stage") && i+1 >= num){
          printf("Error: Not enough arguments to the -stage option.\n");
          return -1;
        }
      }
      else{
        printf("Error: Unknown option: %s\n" , input[i]);
        return -1;
      }
    }
  }
  if(reqN != 1){
    printf("Error: -n option required to be specified.\n");
    return -1;
  }
  printResult(iterations, debug, stage);
  return 0;
}

void printResult(int inter, int debugToggle, char * s){
  printf("#-----------------------\n");
  printf("# Iterations : %d\n", inter);
  if(debugToggle){
    printf("# Debugging : Enabled\n");
  }
  else{
    printf("# Debugging : Disabled (Default)\n");
  }
  if(s != NULL){
    printf("# Stage(s)  : %s\n", s);
  }
  else{
    printf("# Stage(s)  : All (Default)\n");
  }
  printf("#-----------------------\n");

}
