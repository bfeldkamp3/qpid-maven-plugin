import static org.junit.Assert.assertFalse

def file = new File(basedir, "build.log")
assertFalse 'Broker should not have started', file.text.contains("Started the Qpid Broker")
assertFalse 'Should not attempt to stop the broker', file.text.contains('Stopped the Qpid Broker')
