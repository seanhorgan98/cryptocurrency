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

28/01/20
Updated documentation
Created data structure for storing node network
Cleaned up code
Improved network generation


Deciding which consensus algorithm to go with (PoW) Raft?
Is each main a node?
Add unit test to replace chain function.
Perform double spend checks at nodes
perform transaction validation checks on new blocks
wallet must be linked to node when created
fixing double (quadrouple) spending problem
number of double spends for evaluation
inputs for transactions are not updating correctly
always staying the same. Before it even reaches Transaction.java so must occur in wallet
Block sending 0 earlier on in the process. (Currently blocked in processTransaction
Might actually have been a feature not a bug lol.

Notes for future work:
    - Nodes expiring, going offline.
    - bitcoin scripts

Notes for evaluation:
    - Running with transactions added in main class works but future work could involve an environment to add in transactions while running

    - Decided not to add incentive as all nodes will always be honest
    - Nodes currently do store every single transaction not just block hashs

Notes for background work/design
    - Mention what a node is
    - Mention sybil attacks
    - Maybe mention that it is all command line not GUI

18/02
4 hours finished writing abstract. planned and started introduction. Read HoF

20/02
2 hours reading HoF, started introduction

22/02
3 hours writing introduction

25/02
2 hours starting writing the background section

26/02
2 hours writing bitcoin white paper part of background.

27/02
1 hour finishing bitcoin white paper background section.
2 hours writing book background section and creating analysis structure.

1/03
2 hours tidying up intro and background

2/03
3 hour completing requirements section

3/03
1 hour finishing requirements and planning design section

4/03
1 hour updating documentation

11/03
1 hour planning design

14/03
/1 hour starting design section (transactions and overview)

16/03
1 hour working on the design section

17/03
2 hour design section

18/03
3 hours working on design section

19/03
1 hour implementation planning
1 hour planning extra sections for design

20/03
1 hour more implementation planning

21/03
30 mins adding section to design
1 hour starting implementation section (Software development process)

22/03
2 hours proof reading work done so far
1 hour writing StringUtil implementation section
1 hour writing blockchain implementation section

23/03
1 hour added testing section for implementation

24/03
1 hour adding nearby nodes section and some writing on tests
2 hours filling out more sections in implementation (Full process)

27/03
1 hour planning evaluation section
2 hours setting up unit testing
1 hour working on test harness

28/03
1 hour starting evaluation section
2 hours adding two test sections to evaluation (double spending & sybil attacks)
1 hour adding third test section to evaluation

29/03
1 hour designing evaluation more

30/03
2 hours completing tests for evaluation
2 hours correcting test harness bug