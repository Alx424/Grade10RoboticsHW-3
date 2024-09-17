# Scouting Software Example
<b><i>An example of something that would be used to record data on games during comps</i></b>

## How To Use
At the start, you can select whether you'd like to write new data or read team data.

*Enter 1 for reading, 2 for entering data*

<code>Hello World!
  Enter 1 to read, 2 to write:
  \> 1</code>

### Reading
When prompted, enter the number of the team you'd like to check. 

The results show the average notes scored in the amp, the speaker, or in total. It shows the percentage of parks, hangs, and traps.

<code>Hello world!
1 to read, 2 to write: 
1
Team number (type "0" to see leaderboard): 
5036
** STATS FOR TEAM 5036 **
Total Recorded Matches: 1
Avg Notes per Match: 4.0
Avg Speakers per Match: 3.0
Avg Amps per Match: 1.0
1
Park: 100.0%
0
Climb: 0.0%
0
Trap: 0.0%</code>

If you'd like to see a leaderboard of all teams ordered by most notes scored per matches, *enter 0*.

<code>Team number (type "0" to see leaderboard): 
\> 0
** TEAM LEADERBOARD **
9999 -> 33 notes per match
2056 -> 9 notes per match
4092 -> 5 notes per match
5036 -> 4 notes per match
8043 -> 3 notes per match</code>

### Writing
You'll be asked to answer several questions, including the team number, amp/speaker scores, and parks/hangs.

<code>Team number: 
\> 5036
Speakers scored: 
\> 2
Amps scored: 
\> 5
Park? 1 for yes, 2 for no: 
\> 1
Climb? 1 for yes, 2 for no: 
\> 2
Trap? 1 for yes, 2 for no: 
\> 2
Log another team? 1 for yes, 2 for no:
\> 2</code>

After writing, you'll be asked if you want to write for another team or view team info

<code>Log another team? 1 for yes, 2 for no
\> 2</code>

## Credits
- Me (Alexander Kula)
- The Internet
