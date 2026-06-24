## 7. API Contract

`POST /api/v1/customers`
request body
```
{
"full_name" : "natanael"
"email"  : "nael@gmail.com"
"phone_number" : "08123456789"
}

```
success response
```
200 OK
{
"id" = 1
"full_name" : "natanael"
"email"  : "nael@gmail.com"
"phone_number" : "08123456789"
}
```
error response
```
400 Bad Request
{
    "code": "INVALID_REQUEST",
    "message": "Invalid Request",
    "fieldErrorResponses": [
        {
            "field": "email",
            "message": "must be a well-formed email address"
        }
    ]
}
```
status code
```
200,400,404,500
```
-----------------------------------------------------
`GET/api/v3/customers`
request body
```
-
```
success response
```
200 OK
{
    "id" : 1,
    "full_name" : "natanael",
    "email" : "nael@gmail.com",
    "phone_number" : "08123456789"
}
```
```
error response
500 Internal Server Error
{
    "code": "INTERNAL_SERVER_ERROR",
    "message": "Internal Server Error",
    "fieldErrorResponses": []
}
```
```
status code
200, 500
```
-----------------------------------------------------
`GET/api/v3/customers/1`
request body
```
-
```
success response

```
200 OK
{
    "id" : 1,
    "full_name" : "natanael",
    "email" : "nael@gmail.com",
    "phone_number" : "08123456789"
}
```
error response
```
500 Internal Server Error
{
    "code": "INTERNAL_SERVER_ERROR",
    "message": "Internal Server Error",
    "fieldErrorResponses": []
}
```

status code
```
200, 500
```
---------------------------------------------------
PUT /api/v1/customers/{id}

request body
```
{
    "full_name" : "natanael",
    "email" : "nael@gmail.com",
    "phone_number" : "08123456789"
}
```
success response
```
200 OK
{
    "id" : 1,
    "full_name" : "natanael",
    "email" : "nael@gmail.com",
    "phone_number" : "08123456789"
}
```
error response
```
400 Bad Request
{
    "code": "INVALID_REQUEST",
    "message": "Invalid Request",
    "fieldErrorResponses": [
        {
            "field": "email",
            "message": "must be a well-formed email address"
        }
    ]
}
```

404 Not Found
```
{
    "code": "NOT_FOUND",
    "message": "Customer not found",
    "fieldErrorResponses": []
}
```
status code

```
200, 400, 404
```