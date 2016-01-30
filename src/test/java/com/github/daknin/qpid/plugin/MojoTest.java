package com.github.daknin.qpid.plugin;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created on 30/01/2016.
 */
public class MojoTest {
    @Test
    public void testName() throws Exception {
        String userDir = System.getProperty("user.dir");
        StartQpidBrokerMojo startMojo = new StartQpidBrokerMojo();
        startMojo.setAmqpPort(5792);
        startMojo.setHttpPort(8080);
        startMojo.setQpidHome(userDir + "/src/test/qpid/");
        startMojo.setQpidWork(userDir + "/target/qpid/");
        startMojo.execute();

        assertNotNull(QpidBroker.getBroker());

        StopQpidBrokerMojo stopMojo = new StopQpidBrokerMojo();

        stopMojo.execute();

        assertNull(QpidBroker.getBroker());
    }
}
