# spring-patch-endpoint

Example of how to implement an **HTTP PATCH** endpoint with Spring Boot where
**Bean Validation only validates the fields that were actually sent** in the
request body.

## The problem

In a partial update (PATCH), the client sends only the fields it wants to
change. If the DTO has annotations such as `@NotEmpty`, default validation would
fail for every missing field, since they arrive as `null`. On the other hand,
removing the annotations would let a field explicitly sent with an invalid value
(e.g. `"foo": ""`) pass without validation.

## The solution

The project tracks which fields were present in the JSON and configures
Hibernate Validator to skip the ones that weren't:

| Class | Responsibility |
| --- | --- |
| `PatchRequest` | Interface with `isInformed(String field)`, which tells whether a field was present in the request. |
| `ExampleRequest` | Example DTO. Each setter records the field name in `informedFields`. Since Jackson only calls the setter for properties present in the JSON, the set reflects exactly what was sent. |
| `CustomTraversableResolver` | `TraversableResolver` implementation that, for `PatchRequest` objects, treats only informed fields as reachable/cascadable. Missing fields are not validated. |
| `ValidationConfig` | Registers the resolver via `ValidationConfigurationCustomizer`. |
| `ExampleController` | Exposes `PATCH /v1/examples`, validates the body with `@Valid`, and logs the received fields. |

## Behavior

```bash
# No fields sent: valid (nothing to validate) -> 200
curl -X PATCH localhost:8080/v1/examples -H "Content-Type: application/json" -d '{}'

# Only foo, with a valid value -> 200 (bar is skipped during validation)
curl -X PATCH localhost:8080/v1/examples -H "Content-Type: application/json" -d '{"foo":"abc"}'

# foo sent empty: violates @NotEmpty -> 400
curl -X PATCH localhost:8080/v1/examples -H "Content-Type: application/json" -d '{"foo":""}'

# foo explicitly sent as null: the field was informed, so it is validated -> 400
curl -X PATCH localhost:8080/v1/examples -H "Content-Type: application/json" -d '{"foo":null}'
```

## Requirements

- Java 21
- Maven (or the included wrapper, `mvnw` / `mvnw.cmd`)

Stack: Spring Boot 4.1 (`spring-boot-starter-webmvc` and `spring-boot-starter-validation`).

## Running

```bash
./mvnw spring-boot:run      # Linux/macOS
mvnw.cmd spring-boot:run    # Windows
```

The application starts at `http://localhost:8080`.

## Tests

```bash
./mvnw test
```

## Adding new PATCH DTOs

1. Implement `PatchRequest` in the DTO.
2. In each setter, record the field name (matching the Java property name) in
   the set of informed fields.
3. Annotate the fields with the desired constraints as usual; they will only be
   applied when the field is present in the request.
