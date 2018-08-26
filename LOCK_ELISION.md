# Lock Elision

- Useful technique to eliminate multiple lock that are confined 
- JIT Compiler can recognize using Escape Analysis that the lock never escape the method 
and then it's not possible to use the same lock with two threads
- https://www.ibm.com/developerworks/library/j-jtp10185/index.html

- Before the compiler is able to recognize that, method must be inlined otherwise we can see virtual calls to invoke a method

![Lock Elision](images/lockelision-1.png)

- With disable option `-XX:-EliminateLocks` we can see this line for every inlined call, otherwise with 
eliminate locks enabled, there is no mention about synchronization

![Lock Elision](images/lockelision-2.png)