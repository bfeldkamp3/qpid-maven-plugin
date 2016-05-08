package com.github.daknin.qpid.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;

import java.io.File;

public class QpidBroker {
    private static Broker broker;

    /**
     * @param amqpPort The port that the broker should listen on for amqp requests.
     * @param httpPort The port that the broker should listen on for http requests.
     * @param rmiPort The port that the broker should listen on for rmi requests.
     * @param jmxPort The port that the broker should listen on for jmx requests.
     * @param qpidHomeDir The root directory for any config used by the broker.
     * @param qpidWorkDir The root directory for any data written by the broker.
     * @param initialConfigLocation The config file that the broker should be configured from.
     * @throws MojoExecutionException if the broker cant be started.
     */
    public static void start(int amqpPort, int httpPort, int rmiPort, int jmxPort, String qpidHomeDir, String qpidWorkDir, String initialConfigLocation) throws MojoExecutionException {

        if (broker != null) {
            throw new MojoExecutionException("A local broker is already running");
        }

        try {
            File qpidHome = new File(qpidHomeDir);
            File qpidWork = new File(qpidWorkDir);

            final BrokerOptions brokerOptions = new BrokerOptions();

            brokerOptions.setConfigProperty(BrokerOptions.QPID_AMQP_PORT, String.valueOf(amqpPort));
            brokerOptions.setConfigProperty(BrokerOptions.QPID_HTTP_PORT, String.valueOf(httpPort));
            brokerOptions.setConfigProperty(BrokerOptions.QPID_RMI_PORT, String.valueOf(rmiPort));
            brokerOptions.setConfigProperty(BrokerOptions.QPID_JMX_PORT, String.valueOf(jmxPort));
            brokerOptions.setConfigProperty(BrokerOptions.QPID_HOME_DIR, qpidHome.getAbsolutePath());
            brokerOptions.setConfigProperty(BrokerOptions.QPID_WORK_DIR, qpidWork.getAbsolutePath());
            brokerOptions.setInitialConfigurationLocation(initialConfigLocation);

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
     * @return The broker.
     */
    public static Broker getBroker() {
        return broker;
    }
}
