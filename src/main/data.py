import matplotlib.pyplot as plt
import numpy as np
import csv

difficulties = [1, 2, 3, 4, 5]

averages = [0,0,0,0,0]
ones = np.full(20,1)
twos = np.full(20,2)
threes = np.full(20,3)
fours = np.full(20,4)
fives = np.full(20,5)

with open('/home/sean/development/cryptocurrency/data/raw/data.txt', 'r') as f:
    data = [[float(digit) for digit in line.split(',')] for line in f]

for i in range(0,5):
    averages[i] = np.around(np.mean(data[i]), 2)

d1 = data[0]
d2 = data[1]
d3 = data[2]
d4 = data[3]
d5 = data[4]

# Histogram for averages
plt.style.use('seaborn')
plt.bar(difficulties, averages)
plt.xlabel("Difficulty")
plt.ylabel("Time Elapsed (milliseconds)")
plt.title("Average time elapsed mining (ms) at increasing difficulty settings")
plt.show()

# Scatter for full data
plt.yscale('log')
plt.ylim([10**-3,10**6])
plt.xlabel('Difficulty')
plt.ylabel('Time Elapsed (milliseconds)')
plt.title('Time elapsed mining (ms) at increasing difficulty settings')
plt.xticks(difficulties)
plt.scatter(ones,d1, color='r', alpha=0.3)
plt.scatter(twos,d2, color='r', alpha=0.3)
plt.scatter(threes,d3, color='r', alpha=0.3)
plt.scatter(fours,d4, color='r', alpha=0.3)
plt.scatter(fives,d5, color='r', alpha=0.3)

plt.show()