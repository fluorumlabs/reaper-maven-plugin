# reaper-maven-plugin
Maven plugin that can do `System.exit(0)` on HTTP request
 
## Usage

```xml
<plugin>
    <groupId>com.github.fluorumlabs</groupId>
    <artifactId>reaper-maven-plugin</artifactId>
    <version>1.0.0</version>
    <executions>
        <execution>
            <goals><goal>kill</goal></goals>
        </execution>
    </executions>
    <!--configuration>
        <port>8989</port>
    </configuration-->
</plugin>
```

Then, if maven is started with `mvn reaper:kill jetty:run`, any HTTP request to `http://localhost:8989` will terminate the process.
