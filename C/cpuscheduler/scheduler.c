/*
 *
 *
 * CS 441/541: CPU Scheduler (Project 1)
 */
#include "scheduler.h"


int main(int argc, char **argv) {

  checkArgs(argc, argv);

  return 0;
}

int checkArgs(int numIdx, char **args){
  int stage = 0;
  int q = 0;
  char * file = NULL;

  int i;
  for(i = 0; i < numIdx; i++){
    if(!strcmp(args[i], "-s")){
      stage = atoi(args[i+1]);
      i++;
    }
    else if(!strcmp(args[i], "-q")){
      q = atoi(args[i+1]);
      i++;
    }
    else{
      file = args[i];
    }
  }
  processCmd(stage, q, file);
  return 0;
}

void processCmd(int s, int q, char *file){
//  printf("Stage %d\n", s);
//  printf("quantum %d\n", q);
//  printf("file %s\n", file);

  if(s == 1){
    printf("Scheduler   :  1 FCFS\n");
    printf("Quantum     :  %d\n", q);
    printf("Sch. File   : %s\n", file);
    printf("-------------------------------\n");
    firstCfirstO(q, file);

  }
  else if(s == 2){
    printf("Scheduler   :  2 SJF\n");
    printf("Quantum     :  %d\n", q);
    printf("Sch. File   : %s\n", file);
    printf("-------------------------------\n");
    shortJobF(q, file);
  }
  else if(s == 3){
    printf("Scheduler   :  3 Priority\n");
    printf("Quantum     :  %d\n", q);
    printf("Sch. File   : %s\n", file);
    printf("-------------------------------\n");
    priority(q, file);
  }
  else if(s == 4){
    printf("Scheduler   :  1 RR\n");
    printf("Quantum     :  %d\n", q);
    printf("Sch. File   : %s\n", file);
    printf("-------------------------------\n");
    roundR(q, file);
  }

}

process* processFile(char *file){
  //printf("File: %s\n", file)
  FILE *inFile;
  inFile = fopen(file, "r");
  char *totalProcStr = NULL;
  int totalProcInt = 0;
  char line[256];
  process *array = NULL;
  int i = 0;

  if(inFile){
    totalProcStr = fgets(line, sizeof(line), inFile);
    totalProcInt = atoi(totalProcStr);
    totalNumProcess = totalProcInt;
    //printf("%d\n",totalProcInt );
    array = malloc(sizeof(process) * totalProcInt);

    while(fgets(line, sizeof(line), inFile)){
      process pro;
      int id = atoi(strtok(line, " "));
      int burst = atoi(strtok(NULL, " "));
      int pr = atoi(strtok(NULL, " "));
      array[i] = createProcess(id, burst, pr, &pro);
      i++;
      //printf("%d , %d, %d\n", id, burst, pr );
    }
    fclose(inFile);
  }
  return array;
}

process createProcess(int id, int burst, int pr, process *pro){
  pro->proNum = id;
  pro->cpuBurst = burst;
  pro->proPriotity = pr;
  pro->wait = 0;
  pro->turn = 0;
  pro->timesStopped = 0;

  return *pro;
}

void firstCfirstO(int q, char *file){
  process *proArray = processFile(file);
  int i;
  int wait = 0;
  int turnA = 0;

  for(i = 0; i < totalNumProcess; i++){
    proArray[i].turn += (proArray[i].cpuBurst + turnA);
    turnA = proArray[i].turn;
    proArray[i].wait = wait;
    wait = turnA;
  }
  printArrivalOrder(proArray);
}

void shortJobF(int q, char *file){
  process *proArray = processFile(file);
  process *tmpArray = NULL;
  tmpArray = malloc(sizeof(process) * totalNumProcess);
  process tmp;
  int i, j;
  int wait = 0;
  int turnA = 0;

  for(i = 0; i < totalNumProcess; i++){
    process p;
    tmpArray[i] = createProcess(proArray[i].proNum, proArray[i].cpuBurst, proArray[i].proPriotity, &p);
  }
  for(i = 0; i < totalNumProcess; i++){
    for(j = i + 1; j < totalNumProcess; j++){
      if(tmpArray[i].cpuBurst > tmpArray[j].cpuBurst && tmpArray[i].cpuBurst != tmpArray[j].cpuBurst){
          tmp = tmpArray[i];
          tmpArray[i] = tmpArray[j];
          tmpArray[j] = tmp;
      }
    }
  }
  for(i = 0; i < totalNumProcess; i++){
    for(j = 0; j < totalNumProcess; j++){
      if(tmpArray[i].proNum == proArray[j].proNum){
        proArray[j].turn += (proArray[j].cpuBurst + turnA);
        turnA = proArray[j].turn;
        proArray[j].wait = wait;
        wait = turnA;
      }
    }
  }
  free(tmpArray);
  printArrivalOrder(proArray);
}

void priority(int q, char *file){
  process *proArray = processFile(file);
  process *tmpArray = NULL;
  tmpArray = malloc(sizeof(process) * totalNumProcess);
  process tmp;
  int i, j;
  int wait = 0;
  int turnA = 0;

  for(i = 0; i < totalNumProcess; i++){
    process p;
    tmpArray[i] = createProcess(proArray[i].proNum, proArray[i].cpuBurst, proArray[i].proPriotity, &p);
  }
  for(i = 0; i < totalNumProcess; i++){
    for(j = i + 1; j < totalNumProcess; j++){
      if(tmpArray[i].proPriotity > tmpArray[j].proPriotity && tmpArray[i].proPriotity != tmpArray[j].proPriotity){
          tmp = tmpArray[i];
          tmpArray[i] = tmpArray[j];
          tmpArray[j] = tmp;
      }
    }
  }
  for(i = 0; i < totalNumProcess; i++){
    for(j = 0; j < totalNumProcess; j++){
      if(tmpArray[i].proNum == proArray[j].proNum){
        proArray[j].turn += (proArray[j].cpuBurst + turnA);
        turnA = proArray[j].turn;
        proArray[j].wait = wait;
        wait = turnA;
      }
    }
  }
  free(tmpArray);
  printArrivalOrder(proArray);
}

void roundR(int q, char *file){
  process *proArray = processFile(file);
  int i;
  int wait = 0;
  int turnA = 0;
  int quit = 0;
  int bursts[totalNumProcess];

  for(i = 0; i < totalNumProcess; i++){
    bursts[i] = proArray[i].cpuBurst;
  }

  while(quit != totalNumProcess){
    for(i = 0; i < totalNumProcess; i++){
      if(proArray[i].cpuBurst != 0){
        if(proArray[i].cpuBurst <= q){
          proArray[i].turn += (proArray[i].cpuBurst + turnA);
          proArray[i].cpuBurst -= proArray[i].cpuBurst;
          turnA = proArray[i].turn;
          proArray[i].wait = (wait - (proArray[i].timesStopped * q));
          wait = turnA;
        }
        else{
          proArray[i].turn += q;
          proArray[i].cpuBurst -= q;
          turnA = proArray[i].turn;
          proArray[i].wait = wait;
          wait = turnA;
          proArray[i].timesStopped++;
        }
      }
    }
    quit = 0;
    for(i = 0; i < totalNumProcess; i++){
      if(proArray[i].cpuBurst == 0){
        quit++;
      }
    }
  }
  for(i = 0; i < totalNumProcess; i++){
    proArray[i].cpuBurst = bursts[i];
  }

  printArrivalOrder(proArray);

}

void printArrivalOrder(process *pro){
  printf("Arrival Order:  ");
  int idx;
  for(idx = 0; idx < totalNumProcess; idx++){
    if(idx == (totalNumProcess - 1)){
      printf("%d\n", pro[idx].proNum);
    }
    else{
      printf("%d, ",pro[idx].proNum );
    }
  }
  printProcessInfo(pro);
}

void printProcessInfo(process *pro){
  int i;
  printf("Process Information:\n");
  for(i = 0; i < totalNumProcess; i++){
    printf(" %d	 %d	  %d\n", pro[i].proNum, pro[i].cpuBurst, pro[i].proPriotity);
  }
  printf("-------------------------------\n");
  printRunResults(pro);

}

void printRunResults(process *pro){
  int i;
  int waitTimes[totalNumProcess];
  int turnArTimes[totalNumProcess];
  double avgWait = 0;
  double avgTurnAround = 0;

  printf("Running...\n");
  printf("##################################################\n");
  printf("#  #	CPU	Pri	  W	  T\n");
  for(i = 0; i < totalNumProcess; i++){
    turnArTimes[i] = pro[i].turn;
    waitTimes[i] = pro[i].wait;
    printf("#  %d	 %d	  %d	  %d	 %d\n",pro[i].proNum, pro[i].cpuBurst, pro[i].proPriotity, pro[i].wait, pro[i].turn);
  }
  printf("##################################################\n");
  for(i = 0; i < totalNumProcess; i++){
    avgWait += (double)waitTimes[i];
    avgTurnAround += (double)turnArTimes[i];
  }
  avgWait = avgWait/totalNumProcess;
  avgTurnAround = avgTurnAround/totalNumProcess;
  printf("# Avg. Waiting Time   :  %.2f\n",avgWait );
  printf("# Avg. Turnaround Time:  %.2f\n",avgTurnAround );
  printf("##################################################\n");
}












//
