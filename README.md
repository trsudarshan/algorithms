My practice dump of https://www.coursera.org/learn/introduction-to-algorithms/

Running times:
--------------

-------------------------------------------------------------------------------------------------------------------
|      ALGORITHM          |   WORST-CASE     |                              COMMENTS                              |
-------------------------------------------------------------------------------------------------------------------
| quick-find              |      M x N       |   M = # of union-find operations, N = # of elements                |  
| quick-union (QU)        |      M x N       |                                                                    |  
| weighted QU (WQU)       |   N + M x logN   |                                                                    |  
| path-compressed(PC) QU  |   N + M x logN   |                                                                    |  
| PC-WQU                  |   N + M x log*N  |   log* = # of times logN needs to be taken to reach the value 1    |              
| Improved-PCWQU          |   N + M x f(M,N) |   f is the Ackermann function                                      |
| 

