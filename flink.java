import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class CustomKafkaSource implements SourceFunction<String> {
    private volatile boolean isRunning = true;
    private final KafkaConsumer<String, String> consumer;

    public CustomKafkaSource(Properties kafkaProps) {
        this.consumer = new KafkaConsumer<>(kafkaProps);
    }

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        consumer.subscribe(Collections.singletonList("my-topic"));
        while (isRunning) {
            for (ConsumerRecord<String, String> record : consumer.poll(Duration.ofMillis(100))) {
                ctx.collect(record.value());
            }
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
        consumer.close();
    }
}
