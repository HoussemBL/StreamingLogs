package stream
import org.apache.spark.sql.types._


/****Interface of kafka producers/consumers****/
trait Kafka {
  def wait_time: Long
  def topic: String


  //schema used to read/write logs in kafka topics
    val schema = new StructType()
    .add("ip", StringType)
    .add("xx", StringType)
    .add("username", StringType)
    .add("date", StringType)
    .add("datepart2", StringType)
    .add("operation", StringType)
    .add("acess", StringType)
    .add("status", LongType)
    .add("size", LongType)
    
    
    
     //specify batch interval
   def convertTime :String={
  var processingTime = this.wait_time + " seconds"
  processingTime
  }
  
}