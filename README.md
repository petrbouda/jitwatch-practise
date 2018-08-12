# JITWatch Practise

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