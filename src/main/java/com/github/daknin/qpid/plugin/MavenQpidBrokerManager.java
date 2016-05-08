package com.github.daknin.qpid.plugin;

import org.apache.maven.plugin.MojoExecutionException;

public interface MavenQpidBrokerManager {
    /**
     * Start the broker.
     *
     * @param amqpPort The port that the broker should listen on for amqp requests.
     * @param httpPort The port that the broker should listen on for http requests.
     * @param rmiPort The port that the broker should listen on for rmi requests.
     * @param jmxPort The port that the broker should listen on for jmx requests.
     * @param qpidHomeDir The root directory for any config used by the broker.
     * @param qpidWorkDir The root directory for any data written by the broker.
     * @param initialConfigLocation The config file that the broker should be configured from.
     * @throws MojoExecutionException if the broker cant be started.
     */
    void start(int amqpPort, int httpPort, int rmiPort, int jmxPort, String qpidHomeDir, String qpidWorkDir, String initialConfigLocation) throws MojoExecutionException;

    /**
     * Stop the broker.
     *
     * @throws MojoExecutionException if the broker cant be stopped.
     */
    void stop() throws MojoExecutionException;
}
