#Account Endpoint
##[Home](./Main.md)
---
##`GET /Account/UserInfo`
#### Purpose:
To get metadata about the logged in account.
#### Request:
None.
#### Response:
~~~
{
  "Email": "sample string 1",
  "HasRegistered": true,
  "LoginProvider": "sample string 3"
}
~~~
#### Parameters:
None.
##[Top](#account-endpoint)
---
##`POST /Account/Logout`
#### Purpose: 
To destroy the user session on the backend and make all calls authenticated with the JWT Token invalid
#### Request:
None.
#### Response:
None.
#### Parameters:
None.
##[Top](#account-endpoint)
---
##`GET /Account/ManageInfo?returnUrl={returnUrl}&generateState={generateState}`
#### Purpose:
#### Request:
#### Response:
~~~
{
  "LocalLoginProvider": "sample string 1",
  "Email": "sample string 2",
  "Logins": [
    {
      "LoginProvider": "sample string 1",
      "ProviderKey": "sample string 2"
    },
    {
      "LoginProvider": "sample string 1",
      "ProviderKey": "sample string 2"
    }
  ],
  "ExternalLoginProviders": [
    {
      "Name": "sample string 1",
      "Url": "sample string 2",
      "State": "sample string 3"
    },
    {
      "Name": "sample string 1",
      "Url": "sample string 2",
      "State": "sample string 3"
    }
  ]
}
~~~
#### Parameters:
|Parameter|Type|Info|
|---------|----|----------|
|returnUrl|String||
|generateState|Boolean|default is False|
##[Top](#account-endpoint)
---
##`POST /Account/ChangePassword`
#### Purpose:
To change the password of the logged in user
#### Request:
~~~
{
  "OldPassword": "sample string 1",
  "NewPassword": "sample string 2",
  "ConfirmPassword": "sample string 3"
}
~~~
#### Response:
None.
#### Parameters:
None.
##[Top](#account-endpoint)
---
##`POST /Account/SetPassword`
#### Purpose:
#### Request:
#### Response:
#### Parameters:
##[Top](#account-endpoint)
---
##`POST /Account/AddExternalLogin`
#### Purpose:
#### Request:
#### Response:
#### Parameters:
##[Top](#account-endpoint)
---
##`POST /Account/RemoveLogin`
#### Purpose:
#### Request:
#### Response:
#### Parameters:
##[Top](#account-endpoint)
---
##`GET /Account/ExternalLogin?provider={provider}&error={error}`
#### Purpose:
#### Request:
#### Response:
#### Parameters:
##[Top](#account-endpoint)
---
##`GET /Account/ExternalLogins?returnUrl={returnUrl}&generateState={generateState}`
#### Purpose:
#### Request:
#### Response:
#### Parameters:
##[Top](#account-endpoint)
---
##`POST /Account/Register`
#### Purpose: 
To create for the user.
#### Request:
#### Response:
#### Parameters:
##[Top](#account-endpoint)
---
##`POST /Account/RegisterExternal`
#### Purpose:
Not Implemented.
#### Request:
#### Response:
#### Parameters:
##[Top](#account-endpoint)
---
