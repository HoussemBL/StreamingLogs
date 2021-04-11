package stream
import org.apache.spark.sql.types._
import java.util.Properties
import scala.io.Source

/****Interface of kafka producers/consumers****/
trait Kafka {
  def timewindow: Long
  def topic: String
}


//companion object
object Kafka {

  //schema used to read/write logs in kafka topics
   final val schema = new StructType()
    .add("ip", StringType)
    .add("xx", StringType)
    .add("username", StringType)
    .add("date", StringType)
    .add("datepart2", StringType)
    .add("operation", StringType)
    .add("acess", StringType)
    .add("status", LongType)
    .add("size", LongType)
    
    
    
     //specify batch interval as a string
   def convertTimeToString(wait_time: Long) :String={
  var processingTime = wait_time + " seconds"
  processingTime
  }
   
   
  //access the schema used for reading apache logs
def getschema() :StructType= {
  Kafka.schema
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
}

