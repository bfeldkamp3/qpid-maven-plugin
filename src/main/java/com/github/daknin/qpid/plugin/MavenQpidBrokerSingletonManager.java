package com.github.daknin.qpid.plugin;

import org.apache.maven.plugin.MojoExecutionException;

public class MavenQpidBrokerSingletonManager implements MavenQpidBrokerManager {

    public void start(int amqpPort, int httpPort, int rmiPort, int jmxPort, String qpidHomeDir, String qpidWorkDir, String initialConfigLocation) throws MojoExecutionException {
        QpidBroker.start(amqpPort, httpPort, rmiPort, jmxPort, qpidHomeDir, qpidWorkDir, initialConfigLocation);
    }

    public void stop() throws MojoExecutionException {
        QpidBroker.stop();
    }
}
