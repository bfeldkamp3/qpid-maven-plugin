package com.github.daknin.qpid.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOExceptionWithCause;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.qpid.server.BrokerOptions;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Mojo( name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST )
public class StartQpidBrokerMojo extends AbstractMojo {
    /**
     * The root directory for any config used by the broker.
     */
    @Parameter(property = "qpidHome", defaultValue = "${basedir}/src/test/qpid")
    private String qpidHome;

    /**
     * The root directory for any data written by the broker.
     */
    @Parameter(property = "qpidWork", defaultValue = "${project.build.directory}/qpid")
    private String qpidWork;

    /**
     * The root directory for any data written by the broker.
     */
    @Parameter(property = "initialConfigurationLocation", defaultValue = "${basedir}/qpid/config.json")
    private String initialConfigurationLocation;

    /**
     * The port that the broker should listen on for amqp requests.
     */
    @Parameter(property = "amqpPort", defaultValue = BrokerOptions.DEFAULT_AMQP_PORT_NUMBER)
    private int amqpPort;

    /**
     * The port that the broker should listen on for http requests.
     */
    @Parameter(property = "httpPort", defaultValue = BrokerOptions.DEFAULT_HTTP_PORT_NUMBER)
    private int httpPort;

    /**
     * The port that the broker should listen on for rmi requests.
     */
    @Parameter(property = "rmiPort", defaultValue = BrokerOptions.DEFAULT_RMI_PORT_NUMBER)
    private int rmiPort;

    /**
     * The port that the broker should listen on for jmx requests.
     */
    @Parameter(property = "jmxPort", defaultValue = BrokerOptions.DEFAULT_JMX_PORT_NUMBER)
    private int jmxPort;

    /**
     * Skip execution of the Qpid Broker plugin if set to true
     */
    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;

    /**
     * Broker manager used to start and stop the broker.
     */
    private MavenQpidBrokerManager brokerManager;

    public int getHttpPort() {
        return httpPort;
    }

    public int getAmqpPort() {
        return amqpPort;
    }

    public String getInitialConfigurationLocation() {
        if (initialConfigurationLocation != null) {
            return initialConfigurationLocation;
        } else {
            return qpidHome + "/config.json";
        }
    }

    public String getQpidWork() {
        return qpidWork;
    }

    public String getQpidHome() {
        return qpidHome;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public void setAmqpPort(int amqpPort) {
        this.amqpPort = amqpPort;
    }

    public void setQpidWork(String qpidWork) {
        this.qpidWork = qpidWork;
    }

    public void setQpidHome(String qpidHome) {
        this.qpidHome = qpidHome;
    }

    public void setInitialConfigurationLocation(String initialConfigurationLocation) {
        this.initialConfigurationLocation = initialConfigurationLocation;
    }

    /**
     * Perform the mojo operation, which starts the ActiveMQ broker unless configured to skip it.  Also registers the
     * connector URIs in the maven project properties on startup, which enables the use of variable substitution in
     * the pom.xml file to determine the address of the connector using the standard ${...} syntax.
     *
     * @throws MojoExecutionException
     */
    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skipped execution of Qpid Broker");
            return;
        }

        addQpidSystemProperties();

        getLog().info("Loading Qpid broker");
        getLog().info("QPID_HOME: " + qpidHome);
        getLog().info("QPID_WORK: " + qpidWork);
        getLog().info("Config file: " + getInitialConfigurationLocation());

        getLog().debug("Cleaning " + qpidWork);
        File qpidWorkDir = new File(qpidWork);
        try {
            FileUtils.deleteDirectory(qpidWorkDir);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to clean QPID_WORK", e);
        }

        this.getBrokerManager().start(amqpPort, httpPort, rmiPort, jmxPort, qpidHome, qpidWork, getInitialConfigurationLocation());

        getLog().info("Started the Qpid Broker");
    }

    /**
     * Set system properties
     */
    protected void addQpidSystemProperties() {
        Properties properties = new Properties();
        properties.put("QPID_HOME", qpidHome);
        properties.put("QPID_WORK", qpidWork);

        // Overwrite any custom properties
        System.getProperties().putAll(properties);
    }

    /**
     * Use the configured broker manager, if defined; otherwise, use the default broker manager.
     *
     * @return the broker manager to use.
     */
    protected MavenQpidBrokerManager getBrokerManager () {
        if ( this.brokerManager == null ) {
            this.brokerManager = new MavenQpidBrokerSingletonManager();
        }

        return  this.brokerManager;
    }
}
