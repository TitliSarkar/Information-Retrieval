'''
This code calculates protein-protein similarity among 172 proteins with 108091 keys,in batch mode.

Formula used for similarity calculation: Jaccard's Similarity Coefficient.

The input is available as a folder/directory of '.keys' files each of which contains a set of key-value pairs.

First, a Protein-Key matrix is formed from these files with Protein files as rows and list of unique keys as columns.
Then similarity among all Protein(i,j) pairs are calculated using Jaccard's similarity coefficient and the outputs are 
stored in a  Protein-Protein similarity matrix.
'''
import sys
sys.path.append('c:\\program files\\anaconda3\\lib\\site-packages')
import glob, os
import os, os.path
import csv
import operator
import numpy as np
import pandas as pd
import re
import time

start_time = time.clock()
#Getting all Protein files and saving them in a list'Protein[]' ---------->
Protein =[]
os.chdir("F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet")
for file in glob.glob("*.keys"):
        Protein.append(file)
no_of_proteins = len(Protein)
print("#Proteins = ",no_of_proteins)  

#Sorting all Protein files ------------->
for p in Protein:
        with open(p, "r") as p_file:
                filename = "F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\"+p_file.name
                f = open(filename, "r")
                lines = f.readlines()
                lines.sort(key=lambda a_line: a_line.split()[0])
                f.close()
#print("File sorting Done!")

#Getting all unique keys in a  sorted list 'Keys[]' ---------------->
Keys = []
for pt in Protein:
        with open(pt, "r") as p_file:
                filename = "F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\"+p_file.name
                f = open(filename, "r")
                for line in f:
                        cntnt = line.split()
                        res = list(map(int, cntnt))
                        item = res[0]
                        Keys.append(item) #add only 1st number of each line of the files as keys
                f.close()
                
Keys = np.unique(Keys)
no_unq_keys = Keys.shape[0]
np.save("F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\keys", Keys);
print("#Unique Keys=",no_unq_keys)
#print(" Getting unique keys Done!")

#Forming Protein-Key matrix ---------------------------------------------->
PKmat = np.zeros(shape=(no_of_proteins,no_unq_keys))
#np.save("F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\PKmat", PKmat);
for p in Protein:
        with open(p, "r") as pt_file:
                fname = "F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\"+pt_file.name
                fl = open(fname, "r")
                for line in fl:
                        content = line.split()
                        results = list(map(int, content))
                        r = Protein.index(p)
                        var=np.where(Keys==results[0]) 
                        c=var[0][0]
                        PKmat[r][c] = results[1]
                fl.close()
                
print ("Protein-Key matrix formulation took :",time.clock() - start_time, "seconds.")
np.savetxt("F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\PKmat.txt", PKmat, delimiter=' ')
df = pd.DataFrame(PKmat, columns=Keys)
df.to_csv('F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\pkmat.csv')
#print("PK matrix formulation done!")

#Calculation of similarity for each protein -------------------------->
start_time = time.clock()
similariy = np.zeros(shape=(no_of_proteins,no_of_proteins))
i = 0
for row in PKmat:
    j = 0
    for subrow in PKmat:
        max = np.maximum(row , subrow)
        maxsum = np.sum(max)
        min = np.minimum(row , subrow)
        minsum = np.sum(min)
        sim = maxsum/minsum
        similariy[i][j] = sim
        j += 1
    i += 1
#print (similariy)
dfsim = pd.DataFrame(similariy, columns=Protein)
dfsim.to_csv('F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\Similarity.csv')
print ("Similarity calculation Done!")
print ("Similarity calculation took :",time.clock() - start_time, "seconds.")

