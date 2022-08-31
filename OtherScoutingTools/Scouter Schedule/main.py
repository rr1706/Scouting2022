import os
import random
import math

directory = os.path.join(os.path.dirname(__file__))
file = open(directory+"\\"+'scouters.txt', 'r')
scouters = file.readlines()
for i in range(len(scouters)): scouters[i] = scouters[i].rstrip('\n')
file.close()

print("How many hours?")
hours = input("")

schedule = []
hour = []
pool = []
choice = ""

for i in range(int(hours)):
	hour = []
	for ii in range(6):
		if len(pool) == 0:
			pool = list(scouters)
			random.shuffle(pool)
			for s in hour:
				pool.remove(s)
		choice = random.choice(pool)
		pool.remove(choice)
		hour.append(choice)
	schedule.append(hour)

for i in range(len(schedule)):
	print(f"\nHour {i+1}:")
	for ii in range(len(schedule[i])):
		print(schedule[i][ii])

input()