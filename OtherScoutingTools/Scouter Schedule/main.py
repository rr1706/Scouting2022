import csv
import os
import random
import numpy as np

import math

directory = os.path.join(os.path.dirname(__file__))
file = open(directory+"\\"+'scouters.txt', 'r')
scouters = file.readlines()
for i in range(len(scouters)): scouters[i] = scouters[i].rstrip('\n')
file.close()
pool = []

for i in range(len(scouters)):
    scouters[i] = scouters[i].split(',')

pool = scouters

for i in range(len(pool)): 
    try: 
        pool[i][1] 
    except: 
        pool[i].append("3")



hours = int(input("How many Hours\n"))
csvschedule = []

for ii in range(hours):

    population = []
    weights = []

    pool = np.array(pool)
    population,weights = np.split(pool,2,axis=1)



    weights = weights.flatten()
    population = population.flatten()


    weights = np.array2string(weights)

    weights = weights.replace("'0'",'0.1').replace("'1'",'0.15').replace("'2'",'0.3').replace("'3'",'0.13').replace(" ",",").replace("[","").replace("]","")

    weights = weights.rsplit(",")



    for i in range(len(weights)):
        weights[i] = float(weights[i])

    population = population.tolist()


    
    newpool = []


    for i in range(6):
        x = random.choices(population=population,weights=weights,k=1)

        if( newpool.count(x) == 0):
            newpool = np.array(newpool)
            newpool = np.append(newpool,x)
            newpool = newpool.tolist()
            poppool = []
            poppool = np.vstack([population,weights])

            popindex = np.where(poppool==x[0])
            popindex = str(popindex).replace("(array([0], dtype=int64), array([","").replace("], dtype=int64))","")
            popindex = int(popindex)


            weights = weights[:]
            weights.pop(popindex)
            population = population[:]
            population.remove(x[0])

    temp = (str(newpool).replace("'","").replace("[","").replace("]",""))
    csvschedule.append(temp)

csvschedule = str(csvschedule)
csvschedule = csvschedule.replace("',","\n").replace("'","").replace("[","").replace("]","")

try:
        os.remove(directory+"\\"+"schedule.csv")  # Remove txt files in main dir
except:
    pass

file = open(directory+"\\"+"schedule.csv", "w+")
file.write(csvschedule)
file.close()
print(csvschedule)
print("Finished!")
input()