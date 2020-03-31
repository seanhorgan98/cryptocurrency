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

print d1
print ones

# Histogram for averages
# Scatter graph for full data
plt.plot(difficulties,averages)
plt.xlabel("Difficulty")
plt.ylabel("Milliseconds Elapsed")
plt.title("How long it took to mine")
plt.axis([1,5,0,1300])
plt.xticks(difficulties)
plt.show()


plt.axis([1,5,0,5000])
plt.xlabel('Difficulty')
plt.ylabel('Milliseconds Elapsed')
plt.title('scatter plot')
plt.xticks(difficulties)
plt.scatter(ones,d1, color='r')
plt.scatter(twos,d2, color='r')
plt.scatter(threes,d3, color='r')
plt.scatter(fours,d4, color='r')
plt.scatter(fives,d5, color='r')
plt.show()