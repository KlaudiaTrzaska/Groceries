
# Groceries

A Spring Boot REST API for grocery checkout, quantity-based discounts, and a customer loyalty program.

The application uses PostgreSQL for data persistence, Flyway for database migrations, integrates with an external Prizes service, and publishes checkout events to Amazon SQS.

Local development is supported through Docker Compose and a dedicated Spring Boot `local` profile that does not require AWS credentials.

## Requirements

- Java 17
- Maven 3.9+
- Docker Desktop
- Prizes client artifact (`org.example:Prizes:1.3-SNAPSHOT:client`) installed in the local Maven repository

## Local Development (without AWS)

The application can run locally using Docker Compose for PostgreSQL and a Spring Boot `local` profile that disables the Amazon SQS integration.

### Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/KlaudiaTrzaska/Groceries.git
    cd Groceries
    ```

2. Create a local environment file:

    ```bash
    cp .env.example .env
    ```

3. Configure the database credentials in `.env`:

    ```dotenv
    POSTGRES_DB=postgres
    POSTGRES_USER=groceries
    POSTGRES_PASSWORD=your_local_password
    ```

   Use the password configured for your PostgreSQL user.

   The `.env` file is excluded from Git and must not be committed.

4. Start PostgreSQL:

    ```bash
    docker compose up -d
    ```

5. Start the Spring Boot application:

    ```bash
    ./run-local.sh
    ```

The application is available at:

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI specification: http://localhost:8080/v3/api-docs

### Spring Profiles

The application uses Spring Boot profiles to separate environment-specific configuration.

- `application.properties` contains shared configuration.
- `application-local.properties` contains local PostgreSQL settings and disables the AWS SQS integration.

The `run-local.sh` script loads environment variables from `.env` and starts the application using the `local` profile.

### Database Migrations

Flyway automatically runs database migrations V1–V8 when the application starts.

The migrations create the following business tables:

- `products`
- `discounts`
- `client`

Flyway also maintains the `flyway_schema_history` table to track applied migrations.

To verify the database tables:

```bash
docker exec groceries-postgres \
  psql -U groceries -d postgres -c "\dt"
```

The migrations create the database structure but do not insert sample products or discounts.

Sample data must currently be added manually before testing checkout with a non-empty basket.

A repeatable seed script is planned in [Issue #14](https://github.com/KlaudiaTrzaska/Groceries/issues/14).

### Local AWS Behavior

The `local` profile disables the real Amazon SQS integration.

Instead of publishing checkout events to Amazon SQS, the application logs the generated events.

This allows local development without AWS credentials or a running SQS queue.

Actual SQS message delivery is not tested in the local profile.

### Stopping the Environment

Stop the Spring Boot application using `Ctrl+C`.

Stop PostgreSQL:

```bash
docker compose down
```

This preserves the database volume and its data.

Avoid using `docker compose down -v` unless you intentionally want to delete the local database volume.

## API Endpoints

The application provides the following REST endpoints:

| Method | Endpoint | Description |
|---|---|---|
| POST | `/checkout` | Calculate a basket receipt and process checkout |
| GET | `/discounts/{productName}` | Retrieve discount information for a product |
| POST | `/addClient` | Register a loyalty customer |
| GET | `/points/{phoneNumber}` | Retrieve a customer's loyalty points |
| GET | `/prizes/{phoneNumber}` | List prizes available to a customer |
| POST | `/prizes/{phoneNumber}/{prizeName}` | Claim a prize through the external Prizes service |

### Register a Customer

Example request:

```bash
curl -X POST http://localhost:8080/addClient \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "phoneNumber": "+1234567890"
  }'
```

### Checkout

The checkout endpoint receives a basket containing product names and an optional phone number.

Repeated product names represent multiple quantities.

Example request:

```bash
curl -X POST http://localhost:8080/checkout \
  -H "Content-Type: application/json" \
  -d '{
    "basket": ["bread", "butter", "butter"],
    "phoneNumber": "+1234567890"
  }'
```

The products must already exist in the database.

The response contains:

- `productsMap` — product names mapped to quantities.
- `totalPrice` — final basket total after applicable discounts.
- `totalDiscounts` — subtotal charged for products with discount records, not the actual amount saved.

Registered loyalty customers receive points based on the integer part of the checkout total.

After checkout, the application generates a completed checkout event.

In the local profile, this event is logged instead of being sent to Amazon SQS.

### Check Loyalty Points

Example request:

```bash
curl "http://localhost:8080/points/%2B1234567890"
```

## Build and Tests

Run unit tests:

```bash
mvn test
```

Build the application:

```bash
mvn clean package
```

The project uses JUnit 5 and Mockito to test checkout, discount, and loyalty functionality.

Unit tests mock external dependencies and do not require a running PostgreSQL database or AWS SQS queue.

However, the Prizes client artifact must be available in the local Maven repository for successful compilation.

## Project Structure

```text
src/
├── main/
│   ├── java/org/example/
│   │   ├── controllers/   # REST API endpoints
│   │   ├── dto/           # Request DTOs
│   │   ├── services/      # Business logic and integrations
│   │   ├── data/          # Spring Data JPA repositories
│   │   ├── model/         # Database entities
│   │   ├── events/        # Checkout event payloads
│   │   └── exceptions/    # Application exceptions
│   └── resources/
│       ├── application.properties
│       ├── application-local.properties
│       └── db/migration/ # Flyway migrations
└── test/
    └── java/             # Unit tests

compose.yaml              # Local PostgreSQL
run-local.sh              # Local startup script
.env.example              # Environment configuration template
README.md                 # Project documentation
```

## Known Limitations

- Product and discount data must currently be seeded manually.
- The Prizes client JAR is required for compilation.
- Prize-related operations may require a separately running Prizes service.
- The local profile does not verify actual AWS SQS delivery.
- Checkout processing does not currently guarantee atomicity between database updates and SQS publication.

## Planned Improvements

- Add repeatable sample data seeding for local development ([Issue #14](https://github.com/KlaudiaTrzaska/Groceries/issues/14)).
- Refactor checkout processing to improve readability, database access, and error handling.
- Extend documentation with application architecture diagrams, AWS integration details, and database schema descriptions.
