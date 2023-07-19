public class FlinkOutputTagApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Configurar o consumo do tópico A
        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer<>("topic_A", new SimpleStringSchema(), props));

        // Definir um OutputTag para separar os registros que atendem a uma condição específica
        OutputTag<String> outputTag = new OutputTag<String>("outputTag") {};

        // Aplicar uma transformação condicional e separar os registros usando o OutputTag
        DataStream<String> mainStream = stream.process(new ProcessFunction<String, String>() {
            @Override
            public void processElement(String value, Context ctx, Collector<String> out) throws Exception {
                if (atendeCondicao(value)) {
                    // Encaminhar o registro para o fluxo principal
                    out.collect(value);
                } else {
                    // Encaminhar o registro para o fluxo de saída definido pelo OutputTag
                    ctx.output(outputTag, value);
                }
            }
        });

        // Obter o fluxo separado pelo OutputTag
        DataStream<String> sideOutput = mainStream.getSideOutput(outputTag);

        // Encaminhar os fluxos para os tópicos correspondentes (ou fazer outra operação necessária)
        mainStream.addSink(new FlinkKafkaProducer<>("topic_main", new SimpleStringSchema(), props));
        sideOutput.addSink(new FlinkKafkaProducer<>("topic_side", new SimpleStringSchema(), props));

        env.execute("Flink OutputTag App");
    }

    private static boolean atendeCondicao(String value) {
        // Implemente aqui a condição que você deseja verificar
        // e retorne true se a condição for atendida, ou false caso contrário.
        return true;
    }
}
