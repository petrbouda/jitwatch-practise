# Log Diagnostic

### PrintCompilation Output

- Tier Compilation goes throw all 4 stages

`{timestamp} {compile_id} {method_attributes} {compilation_level} {method_name} ({size} bytes)`

- `timestamp` - Timestamp in milliseconds when this log is printed (when a compiler is invoked to run a compilation task)
- `compile_id` - Ascending order of compilations
- `method_attributes`
| Symbol | Meaning | Description |
|:------:|:--------|:------------|
| % | On stack replacement | This `CompileTask` is an OSR or a standard compilation task |
| s | Synchronized method | The method to compile is declared as `synchronized` |
| ! | Method has exception handlers | The method to compile has one or more exception handlers |
| b | Blocking compilation | Application thread is blocked by this `CompileTask` otherwise `CompileTask` is executed by a background compiler thread |
| n | Native wrapper | Native method is used |
- `compilation_level` - On which compilation level is the going to be compiled
- `method_name` - Name of a class and a particular method
- `size` - Size of the original method, not the compiled one, because task has not been even done yet.

```
java -classpath examples/target/classes \
    -XX:+UnlockDiagnosticVMOptions \
    -XX:+PrintCompilation \
    pbouda.jitwatch.examples.UncommonTrap
     61    1       3       java.lang.StringLatin1::hashCode (42 bytes)
     63    2       3       java.util.concurrent.ConcurrentHashMap::tabAt (22 bytes)
     63    3       3       jdk.internal.misc.Unsafe::getObjectAcquire (7 bytes)
     68    4       3       java.lang.Object::<init> (1 bytes)
     68    5       3       java.lang.String::isLatin1 (19 bytes)
     69    6       3       java.lang.String::hashCode (49 bytes)
     69    7       3       java.lang.String::coder (15 bytes)
     69    9       3       java.util.ImmutableCollections$SetN::probe (60 bytes)
     70    8       3       java.lang.Math::floorMod (10 bytes)
     70   10       3       java.lang.Math::floorDiv (22 bytes)
     71   12       3       java.lang.String::equals (65 bytes)
     71   11       1       java.util.ImmutableCollections$Set0::hashCode (2 bytes)
     71   13       1       java.util.Collections$EmptySet::hashCode (2 bytes)
     72   14       3       java.lang.StringLatin1::equals (36 bytes)
     73   15       3       java.util.Collections::emptySet (4 bytes)
     73   17       4       java.lang.StringLatin1::hashCode (42 bytes)
     ...
    108   38     n 0       java.lang.Object::hashCode (native)
    108   37       3       java.util.ImmutableCollections$SetN::<init> (90 bytes)
    109   39       3       jdk.internal.module.ModuleReferenceImpl::hashCode (56 bytes)
    110   44     n 0       jdk.internal.misc.Unsafe::compareAndSetLong (native)
    110   42   !   3       java.util.concurrent.ConcurrentHashMap::putVal (432 bytes)
    110    2       3       java.util.concurrent.ConcurrentHashMap::tabAt (22 bytes)   made not entrant
    110   48     n 0       jdk.internal.misc.Unsafe::compareAndSetObject (native)
    111   50       4       java.lang.String::hashCode (49 bytes)
```

- Disable Tiered-Compilation and compile everything to C2 directly

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