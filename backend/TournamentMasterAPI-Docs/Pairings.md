# Pairings Endpoint

## [Home](./Home.md)

---

##### NOTE:

* do not confuse id with round number!
* round number is only unique within the same tournament

## `GET /pairings?round=<id>`

###### Returns a list of all pairings belonging to the round


#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

##### NOTE:

* result = id of winning competitor
* if result is null nobody has won yet
* if a competitorId is null then the pairing represents a bye
* a bye is given to players that are not playing this round

``` json
[
    {
        "id": 1,
        "roundId": 1,
        "result": null,
        "competitorId1": 5,
        "competitorId2": null

    },
    {
        "id": 1,
        "roundId": 1,
        "result": 4,
        "competitorId1": 5,
        "competitorId2": 4
    }
]
```

---

## `GET /pairings/<id>`

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
{
    "id": 1,
    "roundId": 1,
    "result": null,
    "competitorId1": 5,
    "competitorId2": 4
}
```

---

## `PUT /pairings/<id>`

###### This is used only to set the winner of a a pairing

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|
|`Content-Type`|`application/json`|

``` json
{
    "id": 1,
    "roundId": 1,
    "result": 5,
    "competitorId1": 5,
    "competitorId2": 4
}
```
#### Response

``` json
{
    "id": 1,
    "roundId": 1,
    "result": 5,
    "competitorId1": 5,
    "competitorId2": 4
}
```