import static org.junit.Assert.assertTrue

def folder = new File(basedir, 'target/qpid/')
if( !folder.exists() ) {
    folder.mkdirs()
}

def file = new File( folder, 'DELETE_ME.txt' )

file.withWriterAppend { w ->
    w << "DELETE ME\n"
}
assertTrue "Should have created DELETE_ME.txt file before running tests", file.text.contains("DELETE ME")
