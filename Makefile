SOURCE_DIR = "./src"
DEST_DIR = "./outdir"
JAVAC = javac
JAVA = java
MAIN = "eu.cloudtm.test.TestMain"
MAIN_JAVA = "./src/eu/cloudtm/test/TestMain.java"

compile:
	mkdir -p ${DEST_DIR}
	${JAVAC} -cp ${SOURCE_DIR} -d ${DEST_DIR} ${MAIN_JAVA}

clean:
	rm -r ${DEST_DIR}

run: compile
	${JAVA} -cp ${DEST_DIR} ${MAIN}
