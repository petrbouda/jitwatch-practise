# Uncommon Trap

- Many optimizations are just results/guesses from profiling statistics
- JVM needs some way how to go back from compiled code to interpreted code if the guess is not correct
- Uncommon Trap is a generated code which must validate assumptions made for the optimization
- Fallback to interpreted code is very expensive

- We can see that the `hotMethod` method was compiled
```
java -classpath examples/target/classes \
    -XX:+UnlockDiagnosticVMOptions \
    -XX:-TieredCompilation \
    -XX:+PrintCompilation \
    pbouda.jitwatch.examples.UncommonTrap
     66    1             java.lang.StringLatin1::hashCode (42 bytes)
     94    2             java.util.ImmutableCollections$SetN$1::hasNext (47 bytes)
    103    3             java.lang.Object::<init> (1 bytes)
    108    3             java.lang.Object::<init> (1 bytes)   made not entrant
    124    4             pbouda.jitwatch.examples.UncommonTrap::hotMethod (13 bytes)
    125    5 %           pbouda.jitwatch.examples.UncommonTrap::main @ 4 (32 bytes)
```

