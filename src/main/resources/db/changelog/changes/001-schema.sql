CREATE TABLE users (
       id UUID PRIMARY KEY,
       name VARCHAR(255),
       type VARCHAR(255) NOT NULL,
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
       removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE car_detail (
        id UUID PRIMARY KEY,
        name VARCHAR(255) NOT NULL UNIQUE,
        delta_price NUMERIC(38, 2) NOT NULL,
        detail_types VARCHAR(255) NOT NULL,
        created_at TIMESTAMP WITH TIME ZONE NOT NULL,
        updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
        removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE car_model (
       id UUID PRIMARY KEY,
       model_brand VARCHAR(255) NOT NULL,
       model_name VARCHAR(255) NOT NULL,
       base_price NUMERIC(38, 2) NOT NULL,
       body_type VARCHAR(255) NOT NULL,
       fuel_type VARCHAR(255) NOT NULL,
       horse_power INTEGER NOT NULL,
       engine_volume DOUBLE PRECISION NOT NULL,
       gearbox_type VARCHAR(255) NOT NULL,
       drive_type VARCHAR(255) NOT NULL,
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
       removed BOOLEAN NOT NULL DEFAULT FALSE,
       CONSTRAINT uk_car_model_brand_name UNIQUE (model_brand, model_name)
);

CREATE TABLE car_detail_compatible_models (
      car_detail_id UUID NOT NULL,
      model_id UUID NOT NULL,
      PRIMARY KEY (car_detail_id, model_id),
      CONSTRAINT fk_car_detail_compatible_models_detail FOREIGN KEY (car_detail_id) REFERENCES car_detail(id),
      CONSTRAINT fk_car_detail_compatible_models_model FOREIGN KEY (model_id) REFERENCES car_model(id)
);

CREATE TABLE car_model_details (
       model_id UUID NOT NULL,
       detail_id UUID NOT NULL,
       PRIMARY KEY (model_id, detail_id),
       CONSTRAINT fk_car_model_details_model FOREIGN KEY (model_id) REFERENCES car_model(id),
       CONSTRAINT fk_car_model_details_detail FOREIGN KEY (detail_id) REFERENCES car_detail(id)
);

CREATE TABLE car_model_available_details (
     model_id UUID NOT NULL,
     detail_id UUID NOT NULL,
     PRIMARY KEY (model_id, detail_id),
     CONSTRAINT fk_car_model_available_details_model FOREIGN KEY (model_id) REFERENCES car_model(id),
     CONSTRAINT fk_car_model_available_details_detail FOREIGN KEY (detail_id) REFERENCES car_detail(id)
);

CREATE TABLE car_configuration (
       id UUID PRIMARY KEY,
       configuration_model_id UUID NOT NULL,
       total_price NUMERIC(38, 2) NOT NULL,
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
       removed BOOLEAN NOT NULL DEFAULT FALSE,
       CONSTRAINT fk_car_configuration_model FOREIGN KEY (configuration_model_id) REFERENCES car_model(id)
);

CREATE TABLE car_configuration_used_details (
        configuration_id UUID NOT NULL,
        detail_id UUID NOT NULL,
        PRIMARY KEY (configuration_id, detail_id),
        CONSTRAINT fk_car_configuration_used_details_configuration FOREIGN KEY (configuration_id) REFERENCES car_configuration(id),
        CONSTRAINT fk_car_configuration_used_details_detail FOREIGN KEY (detail_id) REFERENCES car_detail(id)
);

CREATE TABLE cars (
      id UUID PRIMARY KEY,
      car_name VARCHAR(255) NOT NULL UNIQUE,
      configuration_id UUID NOT NULL UNIQUE,
      color VARCHAR(255) NOT NULL,
      price NUMERIC(38, 2) NOT NULL,
      available_for_sale BOOLEAN NOT NULL,
      available_for_test_drive BOOLEAN NOT NULL,
      created_at TIMESTAMP WITH TIME ZONE NOT NULL,
      updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
      removed BOOLEAN NOT NULL DEFAULT FALSE,
      CONSTRAINT fk_cars_configuration FOREIGN KEY (configuration_id) REFERENCES car_configuration(id)
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
      CONSTRAINT fk_complectation_orders_manager FOREIGN KEY (manager_id) REFERENCES users(id),
      CONSTRAINT fk_complectation_orders_car FOREIGN KEY (car_id) REFERENCES cars(id)
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
     CONSTRAINT fk_in_stock_orders_manager FOREIGN KEY (manager_id) REFERENCES users(id),
     CONSTRAINT fk_in_stock_orders_car FOREIGN KEY (car_id) REFERENCES cars(id)
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
    CONSTRAINT fk_test_drive_request_client FOREIGN KEY (client_id) REFERENCES users(id),
    CONSTRAINT fk_test_drive_request_car FOREIGN KEY (car_id) REFERENCES cars(id),
    CONSTRAINT fk_test_drive_request_model FOREIGN KEY (model_id) REFERENCES car_model(id)
);
