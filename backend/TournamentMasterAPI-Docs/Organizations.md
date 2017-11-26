# Organizations Endpoint

## [Home](./Home.md)

---

## `GET /organizations`

###### Get a list of all organizations the user is a  part of.

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
[
    {
        "id": 1,
        "name": "testorg"
    }
]
```

---

## `GET /organizations/<id>`

###### Get an organization a user is part of by its id if it exists.

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
{
    "id": 1,
    "name": "testorg"
}
```
---
## `POST /organizations`

###### Create a new organization for the user.

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|
|`Content-Type`|`application/json`|

``` json
{
    "name": "testorg"
}
```

#### Response

``` json
{
    "id": 1,
    "name": "testorg"
}
```
---
## `PUT /organizations/<id>`

###### Edit an organization a user is part of by its id if it exists.

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|
|`Content-Type`|`application/json`|

``` json
{
    "id": 1,
    "name": "newtestorg"
}
```

#### Response

``` json
{
    "id": 1,
    "name": "newtestorg",
}
```
---
## `DELETE /organizations/<id>`

###### Delete an organization a user is part of by its id if it exists.

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
{
    "id": 1,
    "name": "testorg",
}
```