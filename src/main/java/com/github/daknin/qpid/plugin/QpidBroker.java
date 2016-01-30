package com.github.daknin.qpid.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;

import java.io.File;

public class QpidBroker {
    private static Broker broker;

    public static void start(int amqpPort, int httpPort, String qpidHomeDir, String qpidWorkDir) throws MojoExecutionException {

        if (broker != null) {
            throw new MojoExecutionException("A local broker is already running");
        }

        try {
            File qpidHome = new File(qpidHomeDir);
            File qpidWork = new File(qpidWorkDir);

            final BrokerOptions brokerOptions = new BrokerOptions();

            brokerOptions.setConfigProperty("qpid.amqp_port", String.valueOf(amqpPort));
            brokerOptions.setConfigProperty("qpid.http_port", String.valueOf(httpPort));
            brokerOptions.setConfigProperty("qpid.home_dir", qpidHome.getAbsolutePath());
            brokerOptions.setConfigProperty("qpid.work_dir", qpidWork.getAbsolutePath());
            brokerOptions.setInitialConfigurationLocation(qpidHome.getAbsolutePath() + "/config.json");

            Broker newBroker = new Broker();
            newBroker.startup(brokerOptions);
            broker = newBroker;
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to start the Qpid Broker", e);
        }
    }

    public static void stop() throws MojoExecutionException {

        if (broker == null) {
            throw new MojoExecutionException("The local broker is not running");
        }

        try {
            broker.shutdown();
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to stop the Qpid Broker", e);
        }
        broker = null;
    }

    /**
     * Return the broker service created.
     */
    public static Broker getBroker() {
        return broker;
    }
}
