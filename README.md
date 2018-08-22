# JITWatch Practise

- JVM JIT Compiler (Basics) - [http://pbouda.dropmark.com/484064/13093996]

```
$ java -version
openjdk version "10.0.2" 2018-07-17
OpenJDK Runtime Environment 18.3 (build 10.0.2+13)
OpenJDK 64-Bit Server VM 18.3 (build 10.0.2+13, mixed mode)
```

- JIT Compiler Thresholds
```
java -XX:+PrintFlagsFinal -version | grep CompileThreshold
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

Mandatory JVM flags for a running program:
```bash
java -classpath examples/target/classes \
    -XX:+UnlockDiagnosticVMOptions \
    -XX:+LogCompilation \
    -XX:+TraceClassLoading \
    -XX:+PrintAssembly \
    pbouda.jitwatch.examples.HelloWorld
```

Additional possible options:
```bash
# Disable tiered compilation (enabled by default on Java 8, optional on Java 7)
export notiered="-XX:-TieredCompilation"

# Enable tiered compilation
export tiered="-XX:+TieredCompilation"

# Disable compressed oops (makes assembly easier to read)
export nocompressedoops="-XX:-UseCompressedOops"
```

### TODO - Examples

- Uncommon Trap
- Null Sanity Check
- Devirtualization (Megamorphic calls)
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