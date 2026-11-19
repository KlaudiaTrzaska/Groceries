
## Local development (without AWS)

The application can run locally using Docker Compose
for PostgreSQL and a Spring Boot `local` profile
that disables the Amazon SQS integration.

### Prerequisites

- Java 17
- Maven 3.9+
- Docker Desktop
- Prizes client artifact installed in the local
  Maven repository

### Setup

1. Clone the repository.

2. Create a local environment file:

   cp .env.example .env

3. Configure POSTGRES_DB, POSTGRES_USER and
   POSTGRES_PASSWORD in .env.

4. Start PostgreSQL:

   docker compose up -d

5. Start the Spring Boot application:

   ./run-local.sh

The application is available at:
http://localhost:8080

Swagger UI:
http://localhost:8080/swagger-ui.html

### Database migrations

Flyway automatically runs database migrations
V1–V8 when the application starts.

The migrations create the following business tables:
- products
- discounts
- client

Flyway also creates flyway_schema_history.

To verify the database:

docker exec groceries-postgres \
psql -U groceries -d postgres -c "\dt"

### Local AWS behavior

The local profile disables AWS SQS integration.

Checkout events are logged instead of being
published to Amazon SQS.

This allows local development without real
AWS credentials or a running SQS queue.

### Known limitations

- Product and discount data must be seeded manually.
- The Prizes client JAR is still required to compile.
- Prize-related operations may require a running
  Prizes service.
- The local profile does not test actual SQS delivery.
