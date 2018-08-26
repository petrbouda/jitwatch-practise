# Uncommon Trap

- Speculative Optimization is a big advantage over C++ (which has to be pessimistic and take into account all possibilities)
- JVM can create some assumption based on profiling and observation
- Many optimizations are just results/guesses from profiling statistics
- JVM needs some way how to go back from compiled code to interpreted code if the guess is not correct
- Uncommon Trap is a generated code which must validate assumptions made for the optimization
- Fallback to interpreted code is very expensive

- We can see that the `hotMethod` method was compiled using C2 in 4 level

`pbouda.jitwatch.examples.UncommonTrap::main @ 4 (25 bytes)   made not entrant`
- `main` method contains complicated/long-running loop which was compiled using On-Stack-Replacement
- After escaping from the loop, the optimization was invalidated (we threw away the optimized frame from stack)

```
java -classpath examples/target/classes -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:-BackgroundCompilation pbouda.jitwatch.examples.UncommonTrap
     50    1    b        java.lang.StringLatin1::hashCode (42 bytes)
     75    2    b        java.util.ImmutableCollections$SetN$1::hasNext (47 bytes)
     94    3    b        pbouda.jitwatch.examples.UncommonTrap::hotMethod (16 bytes)
     94    4 %  b        pbouda.jitwatch.examples.UncommonTrap::main @ 2 (24 bytes)
     96    4 %           pbouda.jitwatch.examples.UncommonTrap::main @ 2 (24 bytes)   made not entrant
```

- In a new run, the uncommon trap was activated and now we can see that optimization for `hotMethod` is no longer valid and used
    - Therefore, it's marked as `made not entrant`
    - The optimization code is no longer used and we have to use interpreter instead

```
java -classpath examples/target/classes -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:-BackgroundCompilation pbouda.jitwatch.examples.UncommonTrap
     58    1    b        java.lang.StringLatin1::hashCode (42 bytes)
     87    2    b        java.util.ImmutableCollections$SetN$1::hasNext (47 bytes)
    105    3    b        pbouda.jitwatch.examples.UncommonTrap::hotMethod (16 bytes)
    106    4 %  b        pbouda.jitwatch.examples.UncommonTrap::main @ 2 (24 bytes)
    108    4 %           pbouda.jitwatch.examples.UncommonTrap::main @ 2 (24 bytes)   made not entrant
    109    3             pbouda.jitwatch.examples.UncommonTrap::hotMethod (16 bytes)   made not entrant
```

- The loop was enabled even after the uncommon trap and we can see that the method was compiled again

```
java -classpath examples/target/classes -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:-BackgroundCompilation pbouda.jitwatch.examples.UncommonTrap
     55    1    b        java.lang.StringLatin1::hashCode (42 bytes)
     82    2    b        java.util.ImmutableCollections$SetN$1::hasNext (47 bytes)
     99    3    b        pbouda.jitwatch.examples.UncommonTrap::hotMethod (13 bytes)
    100    4 %  b        pbouda.jitwatch.examples.UncommonTrap::main @ 4 (57 bytes)
    101    4 %           pbouda.jitwatch.examples.UncommonTrap::main @ 4 (57 bytes)   made not entrant
    101    3             pbouda.jitwatch.examples.UncommonTrap::hotMethod (13 bytes)   made not entrant
    102    5    b        pbouda.jitwatch.examples.UncommonTrap::hotMethod (13 bytes)
    102    6 %  b        pbouda.jitwatch.examples.UncommonTrap::main @ 36 (57 bytes)
    104    6 %           pbouda.jitwatch.examples.UncommonTrap::main @ 36 (57 bytes)   made not entrant
```


Results:
- we can see the price we need to pay when the assumption is not correct and must be deoptimized

```
true - 3661 ns
false - 31968 ns
```

![Uncommon Trap]
(/images/uncommontrap-1.png)
