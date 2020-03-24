__Instructions__

Runs on:
- Java 12
- IntelliJ IDEA


__Steps:__
- first unzip the file: `unzip JeffreyWanModule5Lab1`
- to run the application, run these commands inside the `src` folder:

```
javac PostfixConverter.java LinkedListStack.java Operator.java Operand.java Cpu.java
```

and then
`java PostfixConverter postfixInput.txt convertedPostfix.txt`

That will ingest the postfixInput.txt test cases and output the machine instructions to `convertedPostfix.txt`.

To generate javadocs, run this outside of the src folder:

`javadoc -d doc src/*.java` and view them using `open doc/index.html`