package solution

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.KafkaUtils

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import stream._
import db._

import java.util.Properties
import java.util.Properties
import scala.io.Source
object AnalyzeLOGS {
  def main(args: Array[String]): Unit = {

val   spark=getSpark()

val   Kafkaparameters=readKafkaProperties()    
val kafka_topic=Kafkaparameters.getProperty("kafka_topic")
val  timewindow=Kafkaparameters.getProperty("timewindow").toLong



    val consumer = KafkaConsumer(kafka_topic, timewindow)

    //consuming Kafka topic
    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", consumer.topic)
      .option("startingOffsets", "earliest") // From starting
      .load()

    val df_out = KafkaConsumer.convertStreamToDF(consumer.schema,df)

    val df_read = KafkaConsumer.queryStreamingDF(consumer.convertTime,df_out)

    KafkaConsumer.storeData_mysql(consumer.wait_time,df_read)

    df_read.awaitTermination()

  }
  
  
  //read properties of kafka specified in src.main.resources  
  def readKafkaProperties(): Properties=
  {
     val url = getClass.getResource("/kafka.properties") 
 val source = Source.fromURL(url)
    val Kafkaparameters = new Properties
Kafkaparameters.load(source.bufferedReader())

Kafkaparameters

  }
  
  
  //get spark
  def getSpark():SparkSession={
    val spark:SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("SparkByExample")
      .config("spark.cleaner.referenceTracking.cleanCheckpoints", "true")
      .getOrCreate()
  spark    
  }
}
