#!/bin/sh
JRE_HOME=/usr/lib/jvm/jre-11

LOG_CONF=/etc/cfec/${project.artifactId}/${project.artifactId}-log4j2.xml
LOG_PATH=/var/log/${project.artifactId}
JAVA="$JRE_HOME/bin/java"
JAR="/opt/${project.artifactId}/${project.artifactId}.jar"

JAVA_OPTS="-Dfichier.config=/etc/${project.artifactId}/${project.artifactId}.config.testunit.properties"
JAVA_OPTS="$JAVA_OPTS -Dlog4j.configurationFile=$LOG_CONF -Dchemin.log=$LOG_PATH"


nohup $JAVA $JAVA_OPTS -jar $JAR > /dev/null&
#disown

