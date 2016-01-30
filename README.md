# qpid-maven-plugin [![Build Status](https://travis-ci.org/daknin/qpid-maven-plugin.svg?branch=master)](https://travis-ci.org/daknin/qpid-maven-plugin) [![codecov.io](https://codecov.io/github/daknin/qpid-maven-plugin/coverage.svg?branch=master)](https://codecov.io/github/daknin/qpid-maven-plugin?branch=master)

A maven plugin that configures an Apache Qpid AMQP broker within maven lifecycle. Useful for integration tests that require a running AMQP broker such as RabbitMQ.

Add the plugin to the build section of your pom file.

```
<build>
  <plugins>
    ....
  	<plugin>
      <groupId>com.github.daknin</groupId>
      <artifactId>qpid-maven-plugin</artifactId>
      <version>1.0</version>
      <executions>
        <execution>
          <id>start-qpid</id>
          <phase>pre-integration-test</phase>
          <goals>
            <goal>start</goal>
          </goals>
        </execution>
        <execution>
          <id>stop-qpid</id>
          <phase>post-integration-test</phase>
          <goals>
            <goal>stop</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-failsafe-plugin</artifactId>
      <version>${maven.failsafe.plugin.version}</version>
      <executions>
        <execution>
          <goals>
            <goal>integration-test</goal>
            <goal>verify</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
    ....
  <plugins>
<build>
```

The plugin expects to find Qpid config file in ${basedir}/src/qpid. 
```
myapp
 +-- pom.xml
 \-- src
      +-- qpid
      |    \-- etc
      \-- main
           \-- java
```

A [sample project](src/test/resources) shows this.
