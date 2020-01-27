# Timelog

* Implementing a Cryptocurrency
* Sean Horgan
* 2184253
* Dr Ron Poet

## Guidance

* This file contains the time log for your project. It will be submitted along with your final dissertation.
* **YOU MUST KEEP THIS UP TO DATE AND UNDER VERSION CONTROL.**
* This timelog should be filled out honestly, regularly (daily) and accurately. It is for *your* benefit.
* Follow the structure provided, grouping time by weeks.  Quantise time to the half hour.

## Week 1

### 26 Sep 2019

* *0.5 hour* Meeting with supervisor
* *2 hours* Attending Project Guidance lectures 
* *0.5 hour* Created GitLab repository and cloned the skeleton file structure for the projects

### 29 Sept 2019

* *2 hours* Read Hall of Fame dissertaions

### 30 Sept 2019

* *0.5 hour* Updating all documentation with default info
* *0.5 hour* Updating timelog
* *3 hours* Created Block and Blockchain data structures and began testing

### 8 Oct 2019

* *2 hours* Block validation and starting mining

### 9 Oct 2019

* *1.5 hours* Finished basic mining and started transactions

### 14 Oct 2019

* *2 hours* Created transactions base data structure

### 15 Oct 2019

* *2 hours* Created classes for transaction inputs and outputs

### 2 Nov 2019

* *2 hours* Added some verification for data structures

### 5 Nov 2019

* *2 hours* Worked on dissertation
* *1 hour* Cleaned up code

### 7 Nov 2019

* *2 hours* Added some merkle tree validation
* *1 hour* Started testing whole process

### 10 Nov 2019

* *1.5 hours* Read through some of the HoF dissertations
* *1 hour* Added Abstract to dissertation

### 15 Nov 2019

* *1 hour* Started introducing balances to wallets

### 19 Nov 2019

* *2 hours* Created UTXOs to keep track of unspent coins
* *1 hour* Added wallet balances
* *2 hours* Worked on creating coins

### 23 Nov 2019

* *1 hour* Started work on making coin creation immutable

### 26 Nov 2019

* *1 hour* Started making plan for christmas break

### 29 Nov 2019

* *3 hours* Changed so transactions are now called from wallets

### 3 Dec 2019

* *3 hours* Coin creation changed to be a new transaction in order to be immutable

### 6 Dec 2019




21/01/20
Researching consensus 2 hours
Re-evaluating whole project structure.

Deciding which consensus algorithm to go with (PoW) Raft?
Is each main a node?
Add unit test to replace chain function.
Perform double spend checks at nodes
perform transaction validation checks on new blocks
wallet must be linked to node when created

Notes for future work:
    - Nodes expiring, going offline.

Notes for evaluation:
    - Running with transactions added in main class works but future work could involve an environment to add in transactions while running
