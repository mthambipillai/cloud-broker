package com.streamr.broker;

public class Config {
	public static final String KAFKA_HOST = System.getProperty("kafka.server", "127.0.0.1:9092");
	public static final String KAFKA_GROUP = System.getProperty("kafka.group", "data-dev");
	public static final String KAFKA_TOPIC = System.getProperty("kafka.topic", "data-dev");
	public static final String REDIS_HOST = System.getProperty("redis.host", "127.0.0.1");
	public static final String REDIS_PASSWORD = System.getProperty("redis.password", "");
	public static final String CASSANDRA_HOST = System.getProperty("cassandra.host", "127.0.0.1");
	public static final String CASSANDRA_KEYSPACE = System.getProperty("cassandra.keyspace", "streamr_dev");
	public static final int QUEUE_SIZE = Integer.parseInt(System.getProperty("queuesize", "200"));
	public static final int STATS_INTERVAL_IN_SECS = Integer.parseInt(System.getProperty("statsinterval", "30"));
}
