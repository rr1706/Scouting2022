from ast import Str
import csv
from datetime import datetime
import glob
import os
from shutil import move
import shutil
import sys
import time

files = glob.glob("*.txt")
csvfiles = glob.glob("*.csv")
csvdir = str(os.getcwd())+'/CSVs'


newdir = str(os.getcwd())+'/Files'

if not os.path.isdir(newdir):
  os.mkdir(newdir)

if not os.path.isdir(csvdir):
  os.mkdir(csvdir)

cwd = os.listdir(str(os.getcwd()))
destdir = str(os.getcwd())+'\CSVs'

for i in range(len(csvfiles)):
  if str(csvfiles[i]) != ';.csv':
    shutil.move(csvfiles[i],destdir)

headers = []
data = []

curdir = os.getcwd().split('\\')

for file in files:
  with open(file, 'r') as infile:
    l = {}
    for line in infile:
      fields = line.strip().split(':')
      if len(fields) != 2:
        continue
      if fields[0] not in headers:
        headers.append(fields[0])
      l[fields[0]] = fields[1].strip()
    data.append(l)


t = datetime.now().utctimetuple()
timeh =(str(t[3]))
timem =(str(t[4]))
times =(str(t[5]))


for file in os.listdir(str(os.getcwd())):
  if file[-4:] == ".csv":
    filename = timeh+"-"+timem+"-"+times+"-"+curdir[4]+file
    continue

if len(sys.argv) > 1:
  filename = sys.argv[1]
    

with open(filename, 'w') as outfile:
  w = csv.DictWriter(outfile, fieldnames=headers)
  w.writeheader()
  for line in data:
    w.writerow(line)

for file in files:
  shutil.move(file,newdir)