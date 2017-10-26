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

```
[
    {
        "id": 1,
        "name": "testorg",
        "accountOrganization": [],
        "competitors": [],
        "tournaments": []
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

```
{
    "id": 1,
    "name": "testorg",
    "accountOrganization": [],
    "competitors": [],
    "tournaments": []
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

```
{
    "name": "testorg"
}
```

#### Response

```
{
    "id": 1,
    "name": "testorg",
    "accountOrganization": [],
    "competitors": [],
    "tournaments": []
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

```
{
    "id": 1,
    "name": "newtestorg"
}
```

#### Response

```
{
    "id": 1,
    "name": "newtestorg",
    "accountOrganization": [],
    "competitors": [],
    "tournaments": []
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

```
{
    "id": 1,
    "name": "testorg",
    "accountOrganization": [],
    "competitors": [],
    "tournaments": []
}
```