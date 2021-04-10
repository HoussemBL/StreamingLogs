package stream



 import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.KafkaUtils

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger

case class KafkaProducer(var topic: String, var wait_time:Long) extends Kafka
{
  
  
  
def writelogs_topic(df: DataFrame)={
  

  
  
val ds =  df.selectExpr("CAST(ip AS STRING) AS key", "to_json(struct(*)) AS value")
  val df_writing=ds.writeStream
   .format("kafka")
    .outputMode("append")
   .option("kafka.bootstrap.servers", "localhost:9092")
   .option("topic", topic)
//   .option("path", "/tmp")
   .option("checkpointLocation", "/tmp/checkpoints1")
   .trigger(Trigger.ProcessingTime(convertTime))
   //.trigger(Trigger.Once())
   .start()
    
   
   
   df_writing.awaitTermination()

  
}

}