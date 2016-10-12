import java.util.HashMap

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.storage.StorageLevel

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import _root_.kafka.serializer.{DefaultDecoder, StringDecoder}


object Avergage{

	def main(args: Array[String]): Unit = {
		// Create a local StreamingContext with two working thread and batch interval of 2 second
		val sparkConf = new SparkConf().setAppName("KafkaStreamAvg").setMaster("local[2]")
		val ssc = new StreamingContext(sparkConf, Seconds(2))
		ssc.checkpoint("checkpoint")
		//Define kafka parameters
		val kafkaConf = Map(
		    "metadata.broker.list" -> "localhost:9092",
		    "zookeeper.connect" -> "localhost:2181",
		    "group.id" -> "kafka-spark-streaming",
		    "zookeeper.connection.timeout.ms" -> "1000")

		//Create kafka consumer in a receiver-based approach
		//Map of (topic_name -> numPartitions). We only have one partition.
		//val messages = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc, kafkaConf, Map("avg" -> 1), new StorageLevel())

		//or receiver-less approach (data may be lost)
		val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaConf, Set("avg"))

		//Discard message key and split message values into
		val values = messages.map(_._2)
		val pairs = values.map(v => (v.split(",")(0), v.split(",")(1).toDouble))
		
		//Mapping function to update average
		val mappingFunc = (key: String, value: Option[Double], state: State[(Double,Int)]) => {
		    val stateTuple = state.getOption().getOrElse((0.0, 0))
		    val count = (if (value.isEmpty) 0 else 1) + stateTuple._2
		    val avg = stateTuple._1 + ((value.getOrElse(0.0) - stateTuple._1)/ count)
		    state.update((avg, count))
		    (key, avg)
		}
		//Measure average and update continously
		val stateDstream = pairs.mapWithState(StateSpec.function(mappingFunc))
		//Start the computation
		stateDstream.print()
		ssc.start()
		ssc.awaitTermination()
	}	
}
