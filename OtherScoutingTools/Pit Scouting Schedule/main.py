import os
import random
import math
from operator import itemgetter
import sched
import secrets
import shutil
import numpy as np
import requests



basePath = os.path.dirname(str(os.path.realpath(__file__)))
finish = 0

print("This is able to get all matches, but will only pull qualifiers")

inpt = ""

rawTeams = ""

teams = ""


rawData = input('Enter Event ID'+'\n')


headers = {'X-TBA-Auth-Key' : 'btOVsObxXN4N4pTDIZZqaSH3fLQgLkjKumwYgu0HywfniUwWCy1OueaeGU1tF5MS'}


if rawData[0] == "[":
    while True:
        line = input("")
        if str(line) == "]":
            break

        teams += line

else:
    try:
        teams = str(requests.get("https://www.thebluealliance.com/api/v3/event/"+rawData+"/matches/simple", headers=headers).text)
        if "Invalid endpoint" in teams:
            print("Unable to access the webpage (It's buggy)")

    except:
        print("Error with formatting, please put a valid URL or raw text")

        


# Conversion:

teams = teams \
    .replace("\n", "") \
    .replace(" ", "")\
    .replace("{", "")\
    .replace("}", "")\
    .replace("\"", "")\
    .replace(",frc", ".frc")\
    .replace(",", "\n") \
    .splitlines()


temp = []


for t in teams:  # Only gets team keys
    if len(t) < 9:
        continue

    if (t[:9] == "team_keys") or (t[:13] == "comp_level:qm") or (t[:12] == "match_number"):
        temp.append(t)


teams = []

for i in range(len(temp)-2):
    if "qm" in temp[i+2]:
        #teams.append(temp[i+3][13:]+":")  # Match number
        teams.append(temp[i+1])  # Blue alliance
        teams.append(temp[i])  # Red alliance


# By now all qualifier alliances have been added to teams


temp = []


for t in range(int(len(teams)/3)):
    temp.append(teams[t*3] + teams[t*3 + 1] + teams[t*3 + 2])


teams = temp


for t in range(len(teams)):

    teams[t] = teams[t]\
        .replace("team_keys:[", "")\
        .replace("frc", "")\
        .replace("]", ".")\
        .replace(".", ",")

    teams[t] = teams[t][0:len(teams[t])-1]  # Remove the last comma


temp = []


for t in teams:

    temp.append(t.replace(",", "\n").replace(":", "\n").splitlines())


teams = temp


teams = np.array(teams)
teams = np.unique(teams)
npteams = teams
teams = str(teams.tolist())

teams = teams.replace("'","").replace(" ","\n").replace("[","").replace("]","").replace(",","")


try:
        os.remove("C:/Users/robotics/Documents/Scouting Tools/Pit Scouting Schedule/teams.txt")  # Remove txt files in main dir
except:
    pass

file = open("C:/Users/robotics/Documents/Scouting Tools/Pit Scouting Schedule/teams.txt", "w+")
file.write(teams)
file.close()







directory = os.path.join(os.path.dirname(__file__))

file = open(directory+"\\"+'scouters.txt', 'r')

scouters = file.readlines()

for i in range(len(scouters)): scouters[i] = scouters[i].rstrip('\n')

file.close()

schedule = []

pool = []


teamcount = len(npteams)

schedule = np.array(schedule)
schedule = np.append(schedule, scouters)

pool = npteams.tolist()


for i in range(teamcount):
	for s in range(len(scouters)): #scouter 0,1,2... repeat removing teams
		if(len(pool)>0):
			choice = random.choice(pool)
			pool.remove(choice)
			schedule = np.append(schedule, choice)

counter = 500

def checkarray():
	global schedule
	global counter

	if ((int(len(schedule))/int(len(scouters))+1).is_integer()):
		schedule = schedule.reshape((round(int(len(npteams))/int(len(scouters)))+1),len(scouters))
		

	elif(counter > 0):
		schedule = np.append(schedule,"")
		counter -= 1
		checkarray()

checkarray()
schedule = schedule.transpose()

schedule = schedule.tolist()
schedule = str(schedule)
schedule = schedule.replace("[","").replace("]","\n").replace("'","").replace("\n,","\n")

try:
        os.remove("C:/Users/robotics/Documents/Scouting Tools/Pit Scouting Schedule/schedule.csv")  # Remove txt files in main dir
except:
    pass

file = open("C:/Users/robotics/Documents/Scouting Tools/Pit Scouting Schedule/schedule.csv", "w+")
file.write(schedule)
file.close()
print("Finished!")
input()
