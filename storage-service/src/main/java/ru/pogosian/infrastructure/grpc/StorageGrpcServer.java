package ru.pogosian.infrastructure.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageGrpcServer {
    private final StorageCarGrpcService storageCarGrpcService;

    @Value("${storage-service.grpc.port}")
    private int configuredPort;

    private Server server;

    @PostConstruct
    public void start() throws IOException {
        server = NettyServerBuilder.forPort(configuredPort)
                .addService(storageCarGrpcService)
                .build()
                .start();
        log.info("StorageGrpcServer started, listening on port {}", server.getPort());
    }

    @PreDestroy
    public void stop(){
        if(server != null){
            server.shutdown();
        }
    }
}
