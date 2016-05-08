import static org.junit.Assert.assertTrue

Properties properties = new Properties()
def portsFile = new File(basedir, "target/qpidHome/ports.txt")
portsFile.withInputStream {
    properties.load(it)
}

def file = new File(basedir, "build.log")
assertTrue "Start should have started broker on port ${properties['qpid.amqpPort']}", file.text.contains("Starting : Listening on TCP port ${properties['qpid.amqpPort']}")
assertTrue "Start should have started web interface on port ${properties['qpid.httpPort']}", file.text.contains("Starting : HTTP : Listening on port ${properties['qpid.httpPort']}")
assertTrue "Start should have started rmi on port ${properties['qpid.rmiPort']}", file.text.contains("Starting : RMI Registry : Listening on port ${properties['qpid.rmiPort']}")
assertTrue "Start should have started jmx on port ${properties['qpid.jmxPort']}", file.text.contains("Starting : JMX RMIConnectorServer : Listening on port ${properties['qpid.jmxPort']}")
assertTrue 'Startup should have been invoked', file.text.contains("Started the Qpid Broker")

assertTrue 'Shutdown should have been invoked', file.text.contains("Stopped the Qpid Broker")
