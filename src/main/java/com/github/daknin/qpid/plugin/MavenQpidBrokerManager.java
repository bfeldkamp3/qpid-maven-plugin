package com.github.daknin.qpid.plugin;

import org.apache.maven.plugin.MojoExecutionException;

public interface MavenQpidBrokerManager {
    /**
     * Start the broker.
     *
     * @param amqpPort The port that the broker should listen on for amqp requests.
     * @param httpPort The port that the broker should listen on for http requests.
     * @param qpidHomeDir The root directory for any config used by the broker.
     * @param qpidWorkDir The root directory for any data written by the broker.
     * @throws MojoExecutionException
     */
    void start(int amqpPort, int httpPort, String qpidHomeDir, String qpidWorkDir) throws MojoExecutionException;

    /**
     * Stop the broker.
     *
     * @throws MojoExecutionException
     */
    void stop() throws MojoExecutionException;
}
