package com.github.daknin.qpid.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created on 30/01/2016.
 */
public class MojoTest {
    @Test
    public void testName() throws Exception {
        String userDir = System.getProperty("user.dir");
        String qpidHome = userDir + "/src/test/qpid/";
        String qpidWork = userDir + "/target/qpid/";
        StartQpidBrokerMojo startMojo = new StartQpidBrokerMojo();
        startMojo.setAmqpPort(5792);
        startMojo.setHttpPort(8080);
        startMojo.setQpidHome(qpidHome);
        startMojo.setQpidWork(qpidWork);
        File deleteMeFile = new File(FilenameUtils.concat(qpidWork, "DELETE_ME"));
        FileUtils.touch(deleteMeFile);
        startMojo.execute();

        assertNotNull(QpidBroker.getBroker());
        assertTrue(!deleteMeFile.exists());

        StopQpidBrokerMojo stopMojo = new StopQpidBrokerMojo();

        stopMojo.execute();

        assertNull(QpidBroker.getBroker());
    }
}
