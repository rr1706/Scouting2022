from operator import itemgetter
import os
import shutil
import numpy as np
import requests



basePath = os.path.dirname(str(os.path.realpath(__file__)))
finish = 0

print("This is able to get all matches, but will only pull qualifiers")
while finish == 0:
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
                continue
        except:
            print("Error with formatting, please put a valid URL or raw text")
            continue


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
            teams.append(temp[i+3][13:]+":")  # Match number
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

    # :Conversion


    scoutingTeams = ["", "", "", "", "", "", ""]  # 1 match number and 6 teams per match
    


    allArrays = np.concatenate([temp[t] for t in range(len(teams))])


    shapedArray = allArrays.reshape(len(teams),7)
    
    listArray = shapedArray.tolist()


    for i in range(0,len(listArray)):
        listArray[i][0] = int(listArray[i][0])


    
    sortedlist = sorted(listArray, key=itemgetter(0))


    sortedlist = np.array(sortedlist)
    sortedlist = np.delete(sortedlist, 0, axis=1)

    strsortedlist = str(sortedlist)

    strsortedlist = strsortedlist.replace("'", "").replace('[',"").replace(']',"\n").replace('\n ','\n').replace('\n\n','\n').replace(' ',',')

    #print(strsortedlist)

    saveType = 1

    try:
        os.remove("c:/Users/robotics/PycharmProjects/ScoutingDataConverter/ScoutingTeams.txt")  # Remove txt files in main dir
    except:
        pass

    if saveType == 1:
        file = open("c:/Users/robotics/PycharmProjects/ScoutingDataConverter/ScoutingTeams.txt", "w+")
        file.write(strsortedlist)
        file.close()

    finish = 1
