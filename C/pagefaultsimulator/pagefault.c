/*
 *
 *
 * CS 441/541: Page Fault Algorithm Simulator (Project 6)
 */
#include "pagefault.h"


int main(int argc, char **argv) {
    int i;

    fileName = getCommandLineArguments(argc, argv);
    if(fileName == NULL){
      return 0;
    }
    processFile();
    printf("-------------------------------\n");
    printf("Reference String:\n");
    for(i = 0; i < numOfRefrences; i++){
      if(i % 10 == 0 && i != 0){
        printf("\n%c:%c, ",memoryList[i][0], memoryList[i][2] );
      }
      else if(i + 1 == numOfRefrences){
        printf("%c:%c",memoryList[i][0], memoryList[i][2] );
      }
      else{
        printf("%c:%c, ", memoryList[i][0], memoryList[i][2]);
      }
    }
    printf("\n-------------------------------\n");
    printf("####################################################\n");
    printf("#     Frames    Opt.    FIFO     LRU      SC     ESC\n");
    if(frameToShow == -1){
      for(i = 0; i < 7; i++){
        printf("#          %d        ",i+1 );
        printf("      %d",firstInFirstOut(i+1) );
        printf("      %d",leastRecentlyUsed(i+1) );
        printf("      %d",secondChance(i+1) );
        printf("      %d\n",enhancedSC(i+1) );

      }
    }
    else{
      printf("#          %d        ",frameToShow );
      printf("      %d",firstInFirstOut(frameToShow));
      printf("      %d",leastRecentlyUsed(frameToShow) );
      printf("      %d",secondChance(frameToShow) );
      printf("      %d\n",enhancedSC(frameToShow) );

    }
    printf("####################################################\n");


    return 0;
}

int enhancedSC(int numOfFrames){
  int pageFaults = 0;
  memory memBlocks[numOfFrames];
  int pointer = 0;
  int i;
  int refNum = 0;

  for(i = 0; i < numOfFrames; i++){
    memory tmp;
    tmp.id = -1;
    tmp.bit = 1;
    tmp.eBit = 0;
    memBlocks[i] = tmp;
  }

  while(refNum < numOfRefrences){
    char readOrWrite = memoryList[refNum][2];
    if(pointer >= numOfFrames){
      pointer = 0;
    }
    if(!checkHitEnSecoondC(memBlocks, numOfFrames, memoryList[refNum][0], readOrWrite)){
     if(memBlocks[pointer].id == -1){
        memBlocks[pointer].id = memoryList[refNum][0];
        pageFaults++;
        pointer++;
        refNum++;
      }
      else if(memBlocks[pointer].bit == 0 && memBlocks[pointer].eBit == 0){
        memBlocks[pointer].id = memoryList[refNum][0];
        pageFaults++;
        pointer++;
        refNum++;
      }
      else{
        memBlocks[pointer].bit = 0;
        pointer++;
      }
    }
    else{

      refNum++;
    }
  }
  return pageFaults;
}

int checkHitEnSecoondC(memory *memBlocks, int size, char memToCompare, char rOrW){
  int i;

  for(i = 0; i < size; i++){
    if(memBlocks[i].id == memToCompare){
      if(rOrW == 82 || rOrW == 114){
        memBlocks[i].bit = 1;
      }
      else{
        memBlocks[i].eBit = 1;
      }
      return 1;
    }
  }
  return 0;
}

int secondChance(int numOfFrames){
  int pageFaults = 0;
  memory memBlocks[numOfFrames];
  int pointer = 0;
  int i;
  int refNum = 0;

  for(i = 0; i < numOfFrames; i++){
    memory tmp;
    tmp.id = -1;
    tmp.bit = 1;
    memBlocks[i] = tmp;
  }

  while(refNum < numOfRefrences){
    if(pointer >= numOfFrames){
      pointer = 0;
    }

    if(!checkHitSecoondC(memBlocks, numOfFrames, memoryList[refNum][0])){
     if(memBlocks[pointer].id == -1){
        memBlocks[pointer].id = memoryList[refNum][0];
        pageFaults++;
        pointer++;
        refNum++;
      }
      else if(memBlocks[pointer].bit == 0){
        memBlocks[pointer].id = memoryList[refNum][0];
        memBlocks[pointer].bit = 1;
        pageFaults++;
        pointer++;
        refNum++;
      }
      else{
        memBlocks[pointer].bit = 0;
        pointer++;
      }
    }
    else{

      refNum++;
    }
  }
  return pageFaults;
}

int checkHitSecoondC(memory *memBlocks, int size, char memToCompare){
  int i;

  for(i = 0; i < size; i++){
    if(memBlocks[i].id == memToCompare){
      memBlocks[i].bit = 1;
      return 1;
    }
  }
  return 0;
}

int leastRecentlyUsed(int numOfFrames){
  int pageFaults = 0;
  int *memBlocks = (int *)malloc(sizeof(int )* numOfFrames);
  int *stackLRU = (int *)malloc(sizeof(int )* numOfFrames);
  int pointer = 0;
  int i;
  int refNum = 0;
  int stackRef = 0;

  for(i = 0; i < numOfFrames; i++){
    memBlocks[i] = -1;
  }

  while(refNum < numOfRefrences){
    if(pointer >= numOfFrames){
      pointer = 0;
    }
    if(!checkHit(memBlocks, numOfFrames, memoryList[refNum][0])){
      if(memBlocks[pointer] == -1){
        memBlocks[pointer] = memoryList[refNum][0];
        stackLRU[stackRef] = memBlocks[pointer];
        stackRef++;
        pageFaults++;
        pointer++;
      }
      else{
        memBlocks = shuffleList(stackLRU, numOfFrames, memoryList[refNum][0]);
        stackLRU = memBlocks;
        pageFaults++;
      }
    }
    refNum++;
  }
  return pageFaults;
}

int * shuffleList(int * list, int size, char newMem){
  int *newStack = (int *)malloc(sizeof(int)*size);
  int i;

  for(i = 0; i < size - 1; i++){
    newStack[i] = list[i+1];
  }
  newStack[size-1] = newMem;

  return newStack;


}

int firstInFirstOut(int numOfFrames){
  int pageFaults = 0;
  int memBlocks[numOfFrames];
  int pointer = 0;
  int i;
  int refNum = 0;

  for(i = 0; i < numOfFrames; i++){
    memBlocks[i] = -1;
  }

  while(refNum < numOfRefrences){
    if(pointer >= numOfFrames){
      pointer = 0;
    }
    if(!checkHit(memBlocks, numOfFrames, memoryList[refNum][0])){
     if(memBlocks[pointer] == -1){
        memBlocks[pointer] = memoryList[refNum][0];
        pageFaults++;
        pointer++;
      }
      else if(memBlocks[pointer] != memoryList[refNum][0]){
        memBlocks[pointer] = memoryList[refNum][0];
        pageFaults++;
        pointer++;
      }
    }

    refNum++;

  }
  return pageFaults;
}

int checkHit(int *memBlocks, int size, char memToCompare){
  int i;

  for(i = 0; i < size; i++){
    if(memBlocks[i] == memToCompare){
      return 1;
    }
  }
  return 0;
}

void processFile(){
  FILE *inFile;
  inFile = fopen(fileName, "r");
  char line[256];
  int memListSize;
  int i = 0;

  if(inFile){
    memListSize = atoi(fgets(line, sizeof(line), inFile));
    numOfRefrences = memListSize;
    memoryList = (char **)malloc(sizeof(char *) * memListSize);

    while(fgets(line, sizeof(line), inFile)){
      memoryList[i] = strdup(line);
      i++;
    }
  }
  fclose(inFile);

}


char *getCommandLineArguments(int argc, char **argv){
  int frameCount;

  if(argc == 3){
    printf("Please provide an integer after the -f command\n");
    return NULL;
  }
  if(argc > 2){
    if(!strcmp(argv[1], "-f")){
      frameCount = atoi(argv[2]);
      if(frameCount > 0 && frameCount <= 7){
        frameToShow = frameCount;
      }
      else{
        printf("Error: Must supply an integer argument between 1 and 7 for the -f option\n" );
        return NULL;
      }
      printf("Num. Frames\t:\t%d\nRef. File\t:\t%s\n",frameCount, argv[3] );
      return argv[3];
    }
    else{
      frameCount = atoi(argv[3]);
      if(frameCount > 0 && frameCount <= 7){
        frameToShow = frameCount;
      }
      else{
        printf("Error: Must supply an integer argument between 1 and 7 for the -f option\n" );
        return NULL;
      }

      printf("Num. Frames\t:\t%d\nRef. File\t:\t%s\n",frameCount, argv[1] );
      return argv[1];
    }
  }
  else{
    frameToShow = -1;
    printf("Num. Frames\t:\tALL\nRef. File\t:\t%s\n", argv[1] );

    return argv[1];
  }
}
