#
# import findspark
# findspark.init("/usr/local/lib/spark-3.3.2-bin-hadoop3")
#
# from pyspark.sql.functions import *
# from pyspark.sql.types import *
# from pyspark.sql import *
#
# def kafka_to_cassandra_pipeline():
#
#     # Spark session
#     session = SparkSession.builder \
#         .appName("KafkaToCassandra") \
#         .master("yarn") \
#         .config("spark.yarn.queue", "stream") \
#         .config("spark.jars.packages", "org.apache.spark:spark-sql-kafka-0-10_2.12:3.3.2,com.datastax.spark:spark-cassandra-connector_2.12:3.3.0") \
#         .getOrCreate()
#
#     kafka_bootstrap_servers = 'master01:9092,master02:9092,slave01:9092,slave02:9092,slave03:9092'
#     topic = 'tagmanager'
#
#     schema = StructType(
#         [
#             StructField("projectToken", StringType()),
#             StructField("clientId", LongType()),
#             StructField("projectId", LongType()),
#             StructField("sessionId", StringType()),
#             StructField("event", StringType()),
#             StructField("targetId", StringType()),
#             StructField("positionX", IntegerType()),
#             StructField("positionY", IntegerType()),
#             StructField("location", StringType()),
#             StructField("referrer", StringType()),
#             StructField("timestamp", LongType()),
#             StructField("pageDuration", LongType()),
#             StructField("data", StringType()),
#             StructField("screenSizeX", LongType()),
#             StructField("screenSizeY", LongType()),
#             StructField("targetName", StringType()),
#             StructField("title", StringType()),
#             StructField("userAgent", StringType()),
#             StructField("userLanguage", StringType())
#         ]
#     )
#
#     streaming_df = session \
#         .readStream \
#         .format("kafka") \
#         .option("kafka.bootstrap.servers", kafka_bootstrap_servers) \
#         .option("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer") \
#         .option("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer") \
#         .option("failOnDataLoss", "False") \
#         .option("subscribe", topic) \
#         .load() \
#         .withColumn("key", col("key").cast("string")) \
#         .withColumn("value", from_json(col("value").cast("string"), schema))
#
#     streaming_query = streaming_df.select("key", "value.*") \
#         .withColumnRenamed("projectToken", "project_token") \
#         .withColumnRenamed("clientId", "client_id") \
#         .withColumnRenamed("projectId", "project_id") \
#         .withColumnRenamed("sessionId", "session_id") \
#         .withColumnRenamed("event", "event") \
#         .withColumnRenamed("targetId", "target_id") \
#         .withColumnRenamed("positionX", "position_x") \
#         .withColumnRenamed("positionY", "position_y") \
#         .withColumnRenamed("location", "location") \
#         .withColumnRenamed("prevLocation", "prev_location") \
#         .withColumnRenamed("referrer", "referrer") \
#         .withColumnRenamed("timestamp", "creation_timestamp") \
#         .withColumnRenamed("pageDuration", "page_duration") \
#         .withColumnRenamed("data", "data") \
#         .withColumnRenamed("screenSizeX", "screen_size_x") \
#         .withColumnRenamed("screenSizeY", "screen_size_y") \
#         .withColumnRenamed("targetName", "target_name") \
#         .withColumnRenamed("title", "title") \
#         .withColumnRenamed("userAgent", "user_agent") \
#         .withColumnRenamed("userLanguage", "user_language")
#
#     cassandra_keyspace = "tagmanager"
#     cassandra_table = "stream"
#
#     query = streaming_query.writeStream.outputMode("append") \
#         .format("org.apache.spark.sql.cassandra") \
#         .option("checkpointLocation", "/") \
#         .option("spark.cassandra.connection.host", "master01") \
#         .option("spark.cassandra.connection.port", 9042) \
#         .option("keyspace", cassandra_keyspace) \
#         .option("table", cassandra_table) \
#         .option("spark.cassandra.connection.remoteConnectionsPerExecutor", 10) \
#         .option("spark.cassandra.output.concurrent.writes", 1000) \
#         .option("spark.cassandra.concurrent.reads", 512) \
#         .option("spark.cassandra.output.batch.grouping.buffer.size", 1000) \
#         .option("spark.cassandra.connection.keep_alive_ms", 600000000) \
#         .start()
#
#
#     query.awaitTermination()
#
#     session.stop()
