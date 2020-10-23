# reaper-maven-plugin
Maven plugin that can do `System.exit(0)` on HTTP request
 
## Usage

### With `pom.xml`

```xml
<plugin>
    <groupId>com.github.fluorumlabs</groupId>
    <artifactId>reaper-maven-plugin</artifactId>
    <version>1.0.1</version>
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

### Without `pom.xml`

It is possible to invoke plugin without making any changes to `pom.xml`:

`mvn com.github.fluorumlabs:reaper-maven-plugin:kill jetty:run [-Dreaper.port=8989]`
