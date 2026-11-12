# Grocieries

A Spring Boot REST API for grocery checkout, quantity-based discounts, and a customer loyalty program. It stores products and customers in PostgreSQL, integrates with an external Prizes service, and publishes checkout events to Amazon SQS.

## Requirements

- Java 17 and Maven.
- PostgreSQL reachable by the application.
- The Maven dependency `org.example:Prizes:1.3-SNAPSHOT:client` installed in your local Maven repository or available through a configured repository. This project does not include the Prizes service or its client source; obtain the client artifact from that project before building.
- AWS credentials with permission to send messages to the configured SQS queue.
- A running Prizes service for prize operations, configured as required by the external `PrizeClient` library.

## Configuration

Defaults are defined in [`application.properties`](src/main/resources/application.properties). Override them with environment variables for your environment:

| Environment variable | Purpose / default |
| --- | --- |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5433/postgres` |
| `SPRING_DATASOURCE_USERNAME` | Database user; defaults to `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password; set to match your database |
| `AWS_ACCESS_KEY_ID` | Required AWS access key |
| `AWS_SECRET_ACCESS_KEY` | Required AWS secret key |
| `SPRING_CLOUD_AWS_REGION_STATIC` | AWS region; defaults to `us-east-1` |
| `AWS_SQS_ANALYTICS_QUEUE` | Queue name; defaults to `analytics-groceries-queue` |

For example, after supplying your database and AWS credentials:

```bash
export SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5433/postgres'
export SPRING_DATASOURCE_USERNAME='postgres'
export SPRING_DATASOURCE_PASSWORD='your-database-password'
mvn spring-boot:run
```

Flyway applies the migrations in `src/main/resources/db/migration` on startup. Hibernate validates the resulting schema. The migrations create tables but do not insert sample products or discounts; populate these in PostgreSQL before checking out a nonempty basket. There is no REST endpoint for creating products or discounts.

The API runs at `http://localhost:8080` by default.

## API

| Method | Path | Purpose |
| --- | --- | --- |
| `POST` | `/checkout` | Calculate a basket receipt and publish a checkout event |
| `GET` | `/discounts/{productName}` | Look up a product's discount |
| `POST` | `/addClient` | Register a loyalty customer |
| `GET` | `/points/{phoneNumber}` | Check a customer's loyalty points |
| `GET` | `/prizes/{phoneNumber}` | List prizes within a customer's points balance |
| `POST` | `/prizes/{phoneNumber}/{prizeName}` | Claim a prize through the Prizes service |

### Register a customer

```bash
curl -X POST http://localhost:8080/addClient \
  -H 'Content-Type: application/json' \
  -d '{"name":"John Doe","phoneNumber":"+1234567890"}'
```

Successful registration returns an empty response body.

### Checkout

The request uses [`CheckoutRequest`](src/main/java/org/example/dto/CheckoutRequest.java): `basket` is an array of product names, with repeated names representing quantities. `phoneNumber` is optional.

```bash
curl -X POST http://localhost:8080/checkout \
  -H 'Content-Type: application/json' \
  -d '{"basket":["bread","butter","butter"],"phoneNumber":"+1234567890"}'
```

The named products must already exist in the database. The JSON receipt contains:

- `productsMap`: product names mapped to quantities.
- `totalPrice`: the final basket total after applicable discounts.
- `totalDiscounts`: the subtotal charged for products with discount records, including their applicable discounts; this field does **not** represent money saved.

A registered customer receives points equal to the integer part of the final total. An omitted or null basket is treated as empty. Each checkout sends an SQS event containing `eventName` (`completed checkout`), `checkoutDate`, and `totalPrice`. Publishing happens synchronously, so an SQS failure can fail the request after loyalty points have been updated.

### Check points

```bash
curl 'http://localhost:8080/points/%2B1234567890'
```

Interactive API documentation is available at [Swagger UI](http://localhost:8080/swagger-ui.html), with the [OpenAPI specification](http://localhost:8080/v3/api-docs) available as JSON.

## Build and test

```bash
mvn test
mvn package
java -jar target/Grocieries-1.0-SNAPSHOT.jar
```

The unit tests use JUnit 5 and Mockito for checkout, discount, and loyalty behavior. They mock external dependencies and do not require a running database, SQS queue, or Prizes service, but Maven still needs the Prizes client artifact to compile.

## Project layout

```text
src/main/java/org/example/
  controllers/   REST endpoints
  dto/           Request payloads
  services/      Checkout, discounts, loyalty, and SQS publishing
  data/          Database repositories
  model/         Database entities
  events/        Checkout event payload
  exceptions/    Prize-related errors
src/main/resources/
  application.properties
  db/migration/ Flyway schema migrations
src/test/java/  Unit tests
```
