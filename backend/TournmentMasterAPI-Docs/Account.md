#Account Endpoint
This is relatively rudimentary but should be sufficient for now!
##[Home](./Main.md)
---
##`POST /Account/Register`
#### Purpose: 
To create an account with the app
#### Request:
~~~
{
    "Email": "sample string",
    "Password": "sample string",
    "ConfirmPassword": "sample string"
}
~~~
#### Response:
None.
#### Parameters:
None.
##[Top](#account-endpoint)
---
##`DELETE /Account`
#### Purpose: 
To destroy the user session on the backend and make all calls authenticated with the JWT Token invalid
#### Request:
|Header|Value|
|------|-----|
|Authorization|[Account_Key]|
#### Response:
None.
#### Parameters:
|Param|Type|Description|
|-----|----|-----------|
|Account_Key|String|Used to identify user|
##[Top](#account-endpoint)
---
##`GET /Account/Login`
#### Purpose: 
To receive a key for further API calls
#### Request:
~~~
{
    "username": "sample string"
    "password": "sample string" 
}
~~~
#### Response:
~~~
{
    "key": "sample string"
}
~~~
#### Parameters:
None.
##[Top](#account-endpoint)
---
##`POST /Account/Logout`
#### Purpose: 
Invalidates the key so it is unusable when making requests to the backend
#### Request:
|Header|Value|
|------|-----|
|Authorization|[Account_Key]|
#### Response:
None.
#### Parameters:
None.
##[Top](#account-endpoint)
---
