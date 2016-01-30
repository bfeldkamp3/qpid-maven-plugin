package com.github.daknin.qpid.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo( name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST )
public class StopQpidBrokerMojo extends AbstractMojo {
    private MavenQpidBrokerManager brokerManager;

    /**
     * Skip execution of the Qpid Broker plugin if set to true
     */
    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;

    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skipped execution of Qpid Broker");
            return;
        }

        this.getBrokerManager().stop();

        getLog().info("Stopped the Qpid Broker");
    }

    protected MavenQpidBrokerManager getBrokerManager () {
        if ( this.brokerManager == null ) {
            this.brokerManager = new MavenQpidBrokerSingletonManager();
        }

        return  this.brokerManager;
    }
}
