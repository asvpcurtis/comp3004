# OrganizationAccounts Endpoint

## [Home](./Home.md)

---

## `GET /organizationaccounts/<orgId>`

###### Get a list of all accounts in the organization.

##### NOTE:

* This request is for debug purposes I don't yet see a reason to use this in the front end

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|

#### Response

``` json
[
    {
        "accountId": 1,
        "organizationId": 1
    }
]
```

---

## `POST /organizationaccounts`

###### Add an account to an organization.

#### Request

|Headers||
|-|-|
|`Authorization`|`Bearer <JWT token>`|
|`Content-Type`|`application/json`|

``` json
{
    "accountId": 1,
    "organizationId": 1
}
```

#### Response

``` json
{
    "accountId": 1,
    "organizationId": 1
}
```

---

### NOTE:

* To remove yourself from an organization 
* [goto `DELETE /organizations/<id>`](./Organizations.md)
* Don't worry this doesn't delete the organization just the permissions for the current user