# API Contract
Swagger UI -> http://localhost:8080/swagger-ui.html
OpenAPI JSON -> http://localhost:8080/v3/api-docs

## 1. Search Customer by Email
Endpoint -> `GET /api/v1/customers`

### Request
GET /api/v1/customers?email=edith.d@example.com

### Example Response
```json
{
  "content": [
    {
      "id": 1,
      "full_name": "Edith D",
      "email": "edith.d@example.com",
      "phone_number": "081234567890",
      "created_at": "2026-06-17T10:00:00",
      "updated_at": "2026-06-17T10:00:00"
    }
  ],
  "page": 0,
  "size": 10,
  "total_elements": 1,
  "total_pages": 1
}
```