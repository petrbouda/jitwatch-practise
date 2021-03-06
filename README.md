# JITWatch Practise

- JVM JIT Compiler (Basics) - [http://pbouda.dropmark.com/484064/13093996]

```
$ java -version
  java version "10.0.2" 2018-07-17
  Java(TM) SE Runtime Environment 18.3 (build 10.0.2+13)
  Java HotSpot(TM) 64-Bit Server VM 18.3 (build 10.0.2+13, mixed mode)
```

- JIT Compiler Thresholds
```
java -XX:+PrintFlagsFinal -XX:-TieredCompilation -version | grep CompileThreshold
     intx CompileThreshold                         = 10000                                 {pd product} {default}
   double CompileThresholdScaling                  = 1.000000                                 {product} {default}
    uintx IncreaseFirstTierCompileThresholdAt      = 50                                       {product} {default}
     intx Tier2CompileThreshold                    = 0                                        {product} {default}
     intx Tier3AOTCompileThreshold                 = 15000                                    {product} {default}
     intx Tier3CompileThreshold                    = 2000                                     {product} {default}
     intx Tier4CompileThreshold                    = 15000                                    {product} {default}
```

How to install and run JITWatch:
```bash
git clone https://github.com/AdoptOpenJDK/jitwatch
cd jitwatch
mvn clean compile exec:java
```

## Problem with printing Assembly

```
Could not load hsdis-amd64.dylib; library not loadable; PrintAssembly is disabled
```

- Download the library or use `lib/hsdis-amd64.dylib` for mac and then patch your JVM:
`$JAVA_HOME/lib/hsdis-amd64.dylib`

## Logging

- New JVM Logging tags - `-Xlog:jit\*,compilation\*,codecache\*=trace`
- All available tags - `java -Xlog:help`

```
java -XX:LogFile=<path to file> ...
```

```bash
java -classpath examples/target/classes \
    -XX:-TieredCompilation \
    -XX:+UnlockDiagnosticVMOptions \
    -Xlog:jit\*,compilation\*=trace \
    pbouda.jitwatch.examples.UncommonTrap
```

Mandatory JVM flags for a running program:
```bash
java -classpath examples/target/classes \
    -XX:-TieredCompilation \
    -XX:+UnlockDiagnosticVMOptions \
    -XX:+LogCompilation \
    -XX:+TraceClassLoading \
    -XX:+PrintAssembly \
    pbouda.jitwatch.examples.UncommonTrap
    
```

- For measuring compilation time: `-XX:+CITime`
- Print only a particular method: `-XX:CompileCommand=print,pbouda/jitwatch/examples/UncommonTrap.hotMethod`

### TODO - Examples

- Optimization Locks
- Null Sanity Check
- Devirtualization (Megamorphic calls)
    - Interfaces
    - Classes (Class hierarchy)
- Loop Unrolling
- Biased Locking
- Inlining
- Huge Methods
- Too Many Arguments
- Vectorization (https://en.wikipedia.org/wiki/Automatic_vectorization)
- Tail Recursion
- On Stack Allocation
- Runtime Checks Removal
- Branch Predication
- Dead Code Elimination