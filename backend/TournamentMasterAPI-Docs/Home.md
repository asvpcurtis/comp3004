# Tournament Master REST API

### [README](./../../README.md)
### http://ec2-35-182-254-130.ca-central-1.compute.amazonaws.com/api

### http://localhost/api
---
# [Organizations Endpoint](./Organizations.md) `/organizations`
#### This endpoint allows for the creation of organizations by users
---
# [Competitors Endpoint](./Competitors.md) `/competitors`
#### This endpoint allows for organizers to a manage their competitors for their tournaments
---
# [Tournaments Endpoint](./Tournaments.md) `/tournaments`
#### This endpoint allows for organizers to create and view tournaments
---
# [Rounds Endpoint](./Rounds.md) `/rounds`
#### This endpoint allows for organizers to view rounds of tournaments
---
# [Pairings Endpoint](./Pairings.md) `/pairings`
#### This endpoint allows for organizers to view pairings and update their results
---
# [OrganizationAccounts Endpoint](./OrganizationAccounts.md) `/organizationaccounts`
#### This endpoint allows for organizers add other accounts to an organization
---

## `GET /`

##### NOTE :

* The url should look like `http://localhost/api`

###### Allows the current user's Id.

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
7
```
---


