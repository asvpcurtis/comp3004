# Competitors Endpoint

## [Home](./Home.md)

---

## `GET /competitors?organization=<id>`

###### Returns a list of all competitors belonging to the organizaton

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
[
    {
        "id": 1,
        "firstName": "Bob",
        "lastName": "Saget",
        "email": "user@domain.com",
        "rating": 1200,
        "organizationId": 30,
        "gender": "male"
    }
]
```

---

## `GET /competitors/<id>`

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
{
    "id": 1,
    "firstName": "Bob",
    "lastName": "Saget",
    "email": "user@domain.com",
    "rating": 1200,
    "organizationId": 30,
    "gender": "male"
}
```
---

## `POST /competitors`

###### Add a new competitor

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|
|`Content-Type`|`application/json`|

``` json
{
    "firstName": "Bob",
    "lastName": "Saget",
    "email": "user@domain.com",
    "rating": 1200,
    "organizationId": 30,
    "gender": "male"
}
```

#### Response

``` json
{
    "id": 1,
    "firstName": "Bob",
    "lastName": "Saget",
    "email": "user@domain.com",
    "rating": 1200,
    "organizationId": 30,
    "gender": "male"
}
```
---

## `DELETE /competitors/<id>`

###### Delete the competitor

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
{
    "id": 1,
    "firstName": "Bob",
    "lastName": "Saget",
    "email": "user@domain.com",
    "rating": 1200,
    "organizationId": 30,
    "gender": "male"
}
```
---