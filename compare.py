'''
This code calculates protein-protein similarity among 32 proteins in batch mode.
Formula used for similarity calculation: Jaccard's Similarity Coefficient.
First, a sequential version execution is done.
Then, a parallel version of same algorithm is executed.
The results of both version are compared for equality.
'''
from __future__ import print_function
from __future__ import absolute_import
from pycuda.compiler import SourceModule

import pycuda.driver as drv
import pycuda.autoinit
import pandas as pd
import numpy as np
import time
import csv

# Kernel for similarity calculation 
mod = SourceModule("""
__global__ void similarityGPU(int inputmat[32][32], float similaritymat[32][32])
{
  int r = blockIdx.y * blockDim.y + threadIdx.x;
  int c = blockIdx.x * blockDim.x + threadIdx.y;
  int i = c*32+r;  
  int j = 0, k=0;
  	for(j=0 ; j<32 ; j++){
      float max = 0.0 , min = 0.0;
      for (k=0 ; k<32 ; k++){
          if(inputmat[i][k] > inputmat[j][k]){
              max += inputmat[i][k];
              min += inputmat[j][k];
          }
          else{
              max += inputmat[j][k];
              min += inputmat[i][k];
          }
      }
      similaritymat[i][j] = max/min;
   }
}
""")

# sequential function for similarity calculation 
def calculate(mat, similar):
    i = 0
    for row in mat:
        j = 0
        for subrow in mat:
            max = np.maximum(row , subrow)
            maxsum = np.sum(max)
            min = np.minimum(row , subrow)
            minsum = np.sum(min)
            s = maxsum/minsum
            similar[i][j] = s
            #print(similarity[i][j])
            j += 1
        i += 1
    return similar

#creating input matrix (Ideally, this refers to Protein-key matrix)
mat = np.zeros(shape=(32,32))
mat[0][0]=6
mat[0][1]=4
mat[0][2]=9
mat[1][0]=35
mat[1][1]=22
mat[1][3]=65
mat[2][1]=9
mat[2][2]=30
mat[0][3]=45
mat[2][0]=10
mat[4][0]=37
mat[4][2]=2
mat[4][3]=77
mat[2][3]=12
mat[3][3]=5
mat[3][2]=56

mat2 = mat
mat1 = mat.astype(np.int32)
similarity = np.zeros(shape=(32,32))
similarity1 = similarity.astype(np.float32)

# exexuting parallel kernel for similarity calculation
start_time = time.clock()
similarityGPU = mod.get_function("similarityGPU")
similarityGPU(
        drv.In(mat1), drv.Out(similarity1),
        block=(1024,1,1), grid=(1,1,1))
print ("Total GPU kernel execution took :",time.clock() - start_time, " seconds.")
df = pd.DataFrame(similarity1)
df.to_csv('F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\similarityGPU.csv')

sim = np.zeros(shape=(32,32))
sim1 = sim.astype(np.float32)

# exexuting sequential function for similarity calculation
start_time = time.clock()
nwsim = calculate(mat2, sim)
print ("Total CPU function execution took :",time.clock() - start_time, " seconds.")

df2 = pd.DataFrame(nwsim)
df2.to_csv('similarityCPU.csv')

#comparing results of two versions of the algorithm
seqmat = list(csv.reader(open('similarityCPU.csv')))
gpumat = list(csv.reader(open('F:\\Studies\\Ph.D\\Ph.D Work\\ProteinDataSet\\similarityGPU.csv')))
if all(x in seqmat for x in gpumat):
    print("\nResults are not equal.\n")
else:
    print("\nResults are equal.\n")