package ru.pogosian.infrastructure.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.pogosian.grpc.storage.StorageCarServiceGrpc;

@Configuration
public class StorageClientConfig {
    @Bean
    public RestClient storageRestClient(@Value("${storage-service.base-url:http://localhost:8082}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }

    @Bean(destroyMethod = "shutdown")
    public ManagedChannel storageGrpcChannel(
            @Value("${storage-service.grpc.host}") String host,
            @Value("${storage-service.grpc.port}") int port
    ) {
        return ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public StorageCarServiceGrpc.StorageCarServiceBlockingStub storageCarServiceBlockingStub(ManagedChannel channel) {
        return StorageCarServiceGrpc.newBlockingStub(channel);
    }
}