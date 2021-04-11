# StreamingLogs


# prerequisite
setup of mysql, kafka, and spark

# Preparation step
create a kafka topic called "logs" (see kafkacommands document)

create a database called "mylogs" (see sql document)
The database "mylogs" contains a table "log"  (see sql document)


# Steps to do
Use first the script shell "log_genrator.sh" to trigger an infinite loop of logs generation. Thereby, we have a real time generation of logs.
This script writes logs in a defined repository (please change accordingly the path of the repository)


# Scala project
the name of the project is "StreamLogsProcessing".
Please change accoringly the parameters written in "src.main.resources", e.g, mysql connexions parameters, kafka parameters including the path from it we will produce kafka topic.

The main class to execute are:
 (1) src.main.scala.KafkaProducer
 (2) src.main.scala.KafkaConsumer



