CREATE TABLE users (
       id UUID PRIMARY KEY,
       keycloak_id UUID UNIQUE,
       name VARCHAR(255),
       type VARCHAR(255) NOT NULL,
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
       removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE complectation_car_orders (
      id UUID PRIMARY KEY,
      client_id UUID,
      manager_id UUID,
      car_id UUID,
      stage VARCHAR(255),
      created_at TIMESTAMP WITH TIME ZONE NOT NULL,
      updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
      removed BOOLEAN NOT NULL DEFAULT FALSE,
      CONSTRAINT fk_complectation_orders_client FOREIGN KEY (client_id) REFERENCES users(id),
      CONSTRAINT fk_complectation_orders_manager FOREIGN KEY (manager_id) REFERENCES users(id)
);

CREATE TABLE in_stock_car_orders (
     id UUID PRIMARY KEY,
     client_id UUID,
     manager_id UUID,
     car_id UUID,
     stage VARCHAR(255),
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
     removed BOOLEAN NOT NULL DEFAULT FALSE,
     CONSTRAINT fk_in_stock_orders_client FOREIGN KEY (client_id) REFERENCES users(id),
     CONSTRAINT fk_in_stock_orders_manager FOREIGN KEY (manager_id) REFERENCES users(id)
);

CREATE TABLE test_drive_request (
    id UUID PRIMARY KEY,
    is_car_capable_for_test_drive BOOLEAN NOT NULL,
    client_id UUID NOT NULL,
    car_id UUID NOT NULL,
    model_id UUID NOT NULL,
    test_drive_start_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    removed BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_test_drive_request_client FOREIGN KEY (client_id) REFERENCES users(id)
);
CREATE TABLE processed_events(
     id UUID PRIMARY KEY,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
     removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE outbox_events(
    id UUID PRIMARY KEY,
    aggregate_id UUID NOT NULL,
    routing_key VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    trace_id UUID NOT NULL,
    outbox_status VARCHAR(255) NOT NULL,
    attempts INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

