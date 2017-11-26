# Tournaments Endpoint

## [Home](./Home.md)

---

## `GET /tournaments?organization=<id>`

###### Returns a list of all tournaments belonging to the organizaton

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
[
    {
        "id": 1,
        "organizationId": 30,
        "startDate": "2009-02-15T00:00:00",
        "format": 1,
        "onGoing": true,
        "name": "Chicago Open"
    }
]
```

##### NOTE:

`format` is just an integer enum

* 0 = Swiss
* 1 = Elimination
* 2 = DoubleElimination
* 3 = RoundRobin

---

## `GET /tournaments/<id>`

###### Get just tournament by Id

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
{
    "id": 1,
    "organizationId": 30,
    "startDate": "2009-02-15T00:00:00",
    "format": 1,
    "onGoing": true,
    "name": "Chicago Open"
}
```
---

## `POST /tournaments?seed=manual`

##### NOTE:

* options for your query parameters are
* ?seed=manual
* ?seed=rating
* ?seed=random

###### Add a new tournament

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|
|`Content-Type`|`application/json`|

##### NOTE:
This is an array of competitors
``` json
{
    "tournament": {
        "organizationId": 30,
        "startDate": "2009-02-15T00:00:00",
        "format": 1,
        "onGoing": true,
        "name": "Chicago Open"
    },
    "competitors": [
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
}
```

#### Response

``` json
{
    "id": 1,
    "organizationId": 30,
    "startDate": "2009-02-15T00:00:00",
    "format": 1,
    "onGoing": true,
    "name": "Chicago Open"
}
```
---

## `DELETE /tournaments/<id>`

###### Delete the tournament

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