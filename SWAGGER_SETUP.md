# OpenAPI Swagger Setup - Grocieries

## Overview
Your Grocieries API now has OpenAPI/Swagger documentation enabled. The implementation includes:

✅ **Springdoc-OpenAPI dependency** (v2.3.0) - Auto-generates OpenAPI specs  
✅ **Swagger UI** - Interactive API documentation and testing interface  
✅ **Comprehensive annotations** - All endpoints documented with descriptions, parameters, and response codes  
✅ **Application configuration** - API name and docs paths configured  

## Access Points

Once your application is running:

### 1. **Swagger UI** (Interactive Testing)
```
http://localhost:8080/swagger-ui.html
```
- Browse all endpoints by tag (Checkout, Loyalty)
- View detailed operation descriptions
- Try out endpoints directly in the browser
- See request/response examples

### 2. **OpenAPI JSON Spec**
```
http://localhost:8080/v3/api-docs
```
- Raw OpenAPI 3.0 specification
- Use this with OpenAPI client generators
- For integration with external tools

### 3. **OpenAPI YAML Spec** 
```
http://localhost:8080/v3/api-docs.yaml
```
- YAML format of the OpenAPI specification

## Documented Endpoints

### Checkout API (Tag: "Checkout")
- **GET** `/discounts/{productName}` - Get discount by product name

### Loyalty API (Tag: "Loyalty")
- **GET** `/addClient` - Add a new client to the loyalty program
- **GET** `/points/{phoneNumber}` - Get client loyalty points
- **GET** `/prizes/{phoneNumber}` - Get available prizes for client

## Quick Start

1. **Start your application** (ensure PostgreSQL is running):
   ```bash
   mvn spring-boot:run
   ```

2. **Navigate to Swagger UI**:
   ```
   http://localhost:8080/swagger-ui.html
   ```

3. **Test an endpoint**: Click on any endpoint to expand it and click "Try it out"

## Configuration

The following properties are configured in `application.properties`:
- `spring.application.name=Grocieries API` - Sets the API title
- `springdoc.swagger-ui.path=/swagger-ui.html` - Swagger UI location
- `springdoc.api-docs.path=/v3/api-docs` - OpenAPI spec location

## Documentation Details Added

Each endpoint now includes:
- **Operation Summary** - Brief description of what the endpoint does
- **Detailed Description** - More context about the operation
- **Parameters** - Input parameters with descriptions and required flags
- **Response Codes** - HTTP status codes (200, 404, 400) with descriptions
- **Response Content Type** - JSON media type specifications

## Next Steps (Optional)

1. **Enhance Documentation**: Add request/response examples in annotations
2. **API Versioning**: Consider prefixing endpoints with `/api/v1/`
3. **Security Schemes**: Add security documentation if authentication is implemented
4. **Model Documentation**: Add `@Schema` annotations to DTOs and Model classes

---
**Last Updated**: April 26, 2026  
**Springdoc-OpenAPI Version**: 2.3.0  
**Spring Boot Version**: 3.2.4

