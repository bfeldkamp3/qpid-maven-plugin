package com.github.daknin.qpid.plugin;

import org.apache.maven.plugin.MojoExecutionException;

public class MavenQpidBrokerSingletonManager implements MavenQpidBrokerManager {

    public void start(int amqpPort, int httpPort, String qpidHomeDir, String qpidWorkDir) throws MojoExecutionException {
        QpidBroker.start(amqpPort, httpPort, qpidHomeDir, qpidWorkDir);
    }

    public void stop() throws MojoExecutionException {
        QpidBroker.stop();
    }
}
