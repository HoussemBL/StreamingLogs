package solution

import org.apache.spark._
import org.apache.spark.streaming._
//import org.apache.spark.streaming.kafka010._
//import org.apache.spark.streaming.kafka010.KafkaUtils

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import stream._
import db._
import java.io.FileInputStream
import java.util.Properties
import scala.io.Source

object CollectLOGS {
  def main(args: Array[String]): Unit = {

          val spark:SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("SparkByExample")
      .config("spark.cleaner.referenceTracking.cleanCheckpoints", "true")
      .getOrCreate()
//        println("Give the path of directory to be used by the kafka producer \n")    
//         val path_datasource = Console.readLine()
//      // Thread.sleep(10000000)
//     println("path used to produce kafka --> "+ path_datasource)
//     
 
     
val   Kafkaparameters=readKafkaProperties()
    
val path_datasource=Kafkaparameters.getProperty("path_datasource")
val kafka_topic=Kafkaparameters.getProperty("kafka_topic")
val  timewindow=Kafkaparameters.getProperty("timewindow").toLong




   println("path used to produce kafka --> "+ path_datasource)
    println("Name of the kafka topic --> "+ kafka_topic)
    println("Interval of batch in seconds --> "+  timewindow)   
     Thread.sleep(5000)
 
 


 
    val producer = KafkaProducer(kafka_topic,  timewindow)


    //reading stream of logs
    val dfCSV = spark.readStream.option("sep", " ")
      .option("header", "false")
      .schema(producer.schema)
       .csv(path_datasource)
   
     
    
    //writing in kafka topic
     KafkaProducer.writelogs_topic(producer.topic, producer.convertTime,dfCSV)
    
    
    
    
    
    
   

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
