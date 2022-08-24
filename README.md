# Loan App - Java Spring Boot

Sebuah bank ternama yang ada di Indonesia membuka divisi baru yang nantinya
akan dikerjakan oleh squad yang dipilih. Project untuk divisi tersebut adalah
sebuah aplikasi peminjaman online. Aplikasi ini bertujuan untuk mempermudah user
ketika melakukan peminjaman tanpa harus datang ke cabang bank tersebut.

Squad yang dipilih terdiri dari beberapa bagian
diantaranya developer backend/frontend, sqa dan project manager.

Project manager telah menentukan fitur-fitur yang dibutuhkan
dan harus dibuat oleh developer.
Anda terpilih menjadi backend developer pada project tersebut dan
diberikan waktu 2 hari untuk menyelesaikanya.

Harapan SQA, dalam waktu 2 hari backend yang anda kerjakan dapat di test,
sehingga deadline untuk project ini adalah 24 Agustus 2022 pukul 17:00
dipush git dengan sub-group challenge dengan nama repo livecode-loan.

Tester akan melakukan test aplikasi anda secara manual, sehingga
anda harus memastikan bahwa backend yang anda buat sesuai dengan
fitur dan endpoint yang sudah ditentukan dibawah.


## Features

### Auth

- AppUser :

 ```java
class AppUser {
    private String id;
    private String email;
    private String password;
    private List<Role> roles;
}
  ```

- Role :

```java
public class Role {
    private String id;
    private String role; // Enum
}

enum ERole {
    ROLE_CUSTOMER,
    ROLE_STAFF,
    ROLE_ADMIN
}
```

### Loan

- Customer :

```java
class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phone;
    private String status;
    private User user;
    private ProfilePicture profilePicture;
    private List<LoanDocument> loanDocuments;
}
```

- Profile Picture

```java
class ProfilePicture {
    private String id;
    private String name;
    private String contentType;
    private String url;
    private long size;
}
```

- Loan Documents

```java
class LoanDocuments {
    private String id;
    private String name;
    private String contentType;
    private String url;
    private long size;
    private Customer customer;
}
```

- Loan Type

```java
class LoanType {
    private String id;
    private String type;
    private Double maxLoan;
}
```

- Instalment Type

```java
class InstalmentType {
    private String id;
    private EInstalmentType instalmentType;
}

enum EInstalmentType {
    ONE_MONTH,
    THREE_MONTHS,
    SIXTH_MONTHS,
    NINE_MONTHS,
    TWELVE_MONTHS
}
```

- Loan Transaction

```java
class LoanTransaction {
    private String id;
    private LoanType loanType;
    private InstalmentType instalmentType;
    private Customer customer;
    private Double nominal;
    private Long approvedAt;
    private String approvedBy;
    private ApprovalStatus approvalStatus; // enum
    private List<LoanTransactionDetail> loanTransactionDetails;
    private Long createdAt;
    private Long updatedAt;
}

enum ApprovalStatus {
    APPROVED,
    REJECTED
}
```

```java
class GuaranteePicture {
    private String id;
    private String name;
    private String contentType;
    private String path;
    private long size;
}
```

- Loan Transaction Detail

```java
class LoanTransactionDetail {
    private String id;
    private Long transactionDate;
    private Double nominal;
    private LoanTransaction loanTransaction;
    private GuaranteePicture guaranteePicture;
    private LoanStatus loanStatus; // enum
    private Long createdAt;
    private Long updatedAt;
}

enum LoanStatus {
    PAID,
    UNPAID
}
```

## API Documentation

### Auth

#### Register

Request :

- Endpoint : ```/api/auth/signup```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "email": "string",
  "password": "string"
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "email": "string",
    "role": [
      "admin",
      "staff"
    ]
  }
}
```

#### Login

Request :

- Endpoint : ```/api/auth/signin```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "email": "string",
  "password": "string"
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "email": "string",
    "role": [
      "admin",
      "staff"
    ],
    "token": "string"
  }
}
```

### User

#### Get User By Id

Request

- Endpoint : ```/api/users/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
    "email": "string",
    "role": [
      "admin",
      "staff"
    ]
  }
}
```

### Customer

#### Get Customer

Request

- Endpoint : ```/api/customers/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
    "id": "250b8bb1-7d55-458e-b30f-76c7307399bc",
    "firstName": null,
    "lastName": null,
    "dateOfBirth": null,
    "phone": null,
    "status": null,
    "profilePicture": "/customers/250b8bb1-7d55-458e-b30f-76c7307399bc/avatar",
    "loanDocuments": []
  }
}
```

#### Get All Customer

Request

- Endpoint : ```/api/customers```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "Successfully fetch user",
  "data": [
    {
      "id": "250b8bb1-7d55-458e-b30f-76c7307399bc",
      "firstName": null,
      "lastName": null,
      "dateOfBirth": null,
      "phone": null,
      "status": null,
      "profilePicture": "/customers/250b8bb1-7d55-458e-b30f-76c7307399bc/avatar",
      "loanDocuments": []
    },
    {
      "id": "250b8bb1-7d55-458e-b30f-76c7307399bc",
      "firstName": null,
      "lastName": null,
      "dateOfBirth": null,
      "phone": null,
      "status": null,
      "profilePicture": "/customers/250b8bb1-7d55-458e-b30f-76c7307399bc/avatar",
      "loanDocuments": []
    }
  ]
}
```

#### Update Customer

Request :

- Endpoint : ```/api/customers```
- Method : PUT
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "id": "xxx",
  "firstName": "rifqi",
  "lastName": "ramadhan",
  "dateOfBirth": "0000-01-01",
  "phone": "087123",
  "status": 1
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "id": "250b8bb1-7d55-458e-b30f-76c7307399bc",
    "firstName": null,
    "lastName": null,
    "dateOfBirth": null,
    "phone": null,
    "status": null,
    "profilePicture": "/customers/250b8bb1-7d55-458e-b30f-76c7307399bc/avatar",
    "loanDocuments": []
  }
}
```

#### Delete Customer ```*Soft Delete```

Request :

- Endpoint : ```/api/customers/{id}```
- Method : DELETE
- Header :
    - Accept: application/json

Response :

```json
{
  "message": "string",
  "data": null
}
```

#### Upload Avatar

Request :

- Endpoint : ```/api/customers/{customerId}/upload/avatar```
- Method : POST
- Header :
    - Content-type: multipart/form-data
- Form-Data:

```
  avatar : image
```

Response :

```json
{
  "message": "string",
  "data": {
    "name": "string",
    "url": "/api/v1/customers/xxx/avatar"
  }
}
```

#### Download Avatar

Request :

- Endpoint : ```/api/customers/{customerId}/avatar```
- Method : GET
- Header :
    - "content-disposition"

Response :
```Response Image```

### Instalment Type

#### Create Instalment Type

Request :

- Authorize : `ADMIN & STAFF ONLY`
- Endpoint : ```/api/instalment-types```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "instalmentType": "ONE_MONTH"
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
    "instalmentType": "ONE_MONTH"
  }
}
```

#### Get Instalment By Id

Request :

- Endpoint : ```/api/instalment-types/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
    "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
    "instalmentType": "ONE_MONTH"
  }
}
```

#### Get All Instalment-type

Request :

- Endpoint : ```/api/instalment-types```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": [
    {
      "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
      "instalmentType": "ONE_MONTH"
    },
    {
      "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb2a",
      "instalmentType": "THREE_MONTHS"
    }
  ]
}
```

#### Update Instalment-type

Request :

- Authorize : `ADMIN & STAFF ONLY`
- Endpoint : ```/api/instalment-types```
- Method : PUT
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
  "instalmentType": "TWELVE_MONTHS"
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
    "instalmentType": "ONE_MONTH"
  }
}
```

#### Delete Instalment type

Request :

- Endpoint : ```/api/instalment-types/{id}```
- Method : DELETE
- Header :
    - Accept: application/json

Response :

```json
{
  "message": "string",
  "data": null
}
```

### Loan Type

#### Create Loan Type

Request :

- Authorize : `ADMIN & STAFF ONLY`
- Endpoint : ```/api/loan-types```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "type": "Pinjaman Kredit Elektronik",
  "maxLoan": 10000000
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
    "type": "Pinjaman Kredit Elektronik",
    "maxLoan": 10000000
  }
}
```

#### Get Loan By Id

Request :

- Endpoint : ```/api/loan-types/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
    "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
    "type": "Pinjaman Kredit Elektronik",
    "maxLoan": 10000000
  }
}
```

#### Get All Loan-type

Request :

- Endpoint : ```/api/loan-types```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": [
    {
      "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
      "type": "Pinjaman Kredit Elektronik",
      "maxLoan": 10000000
    },
    {
      "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
      "type": "Pinjaman Kredit Kendaraan",
      "maxLoan": 100000000
    }
  ]
}
```

#### Update Loan-type

Request :

- Authorize : `ADMIN & STAFF ONLY`
- Endpoint : ```/api/loan-types```
- Method : PUT
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
  "type": "Pinjaman Kredit Kendaraan",
  "maxLoan": 100000000
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "id": "58aa290c-a84b-48ab-9f18-3fbbeef6bb7d",
    "type": "Pinjaman Kredit Kendaraan",
    "maxLoan": 100000000
  }
}
```

#### Delete Loan type

Request :

- Endpoint : ```/api/loan-types/{id}```
- Method : DELETE
- Header :
    - Accept: application/json

Response :

```json
{
  "message": "string",
  "data": null
}
```

### Transaction

#### Request Loan

Request :

- Authorize : `CUSTOMER ONLY`
- Endpoint : ```/api/transactions```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "loanType": {
    "id": "d781644b-a73f-4d54-ae94-a7b59bff05fc"
  },
  "instalmentType": {
    "id": "3e7023c8-77a8-4311-a195-3958a14a20a1"
  },
  "customer": {
    "id": "250b8bb1-7d55-458e-b30f-76c7307399bc"
  },
  "nominal": 10000000
}
```

Response : ```*CREATE DTO IF YOU WANT DIFFERENT RESPONSE*```

```json
{
  "message": "string",
  "data": {
    "id": "bec5ca14-a867-4f34-a919-62dd6f941dec",
    "loanTypeId": "d781644b-a73f-4d54-ae94-a7b59bff05fc",
    "instalmentTypeId": "3e7023c8-77a8-4311-a195-3958a14a20a1",
    "customerId": "250b8bb1-7d55-458e-b30f-76c7307399bc",
    "nominal": 1.0E7,
    "approvedAt": null,
    "approvedBy": null,
    "approvalStatus": null,
    "transactionDetailResponses": null,
    "createdAt": 1661106557370,
    "updatedAt": null
  }
}
```

#### Get Transaction By id

Request :

- Endpoint : ```/api/transactions/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
    "id": "bec5ca14-a867-4f34-a919-62dd6f941dec",
    "loanTypeId": "d781644b-a73f-4d54-ae94-a7b59bff05fc",
    "instalmentTypeId": "3e7023c8-77a8-4311-a195-3958a14a20a1",
    "customerId": "250b8bb1-7d55-458e-b30f-76c7307399bc",
    "nominal": 1.0E7,
    "approvedAt": 1661091574279,
    "approvedBy": "rifqyomp@gmail.com",
    "approvalStatus": "APPROVED",
    "transactionDetailResponses": [
      {
        "id": "fd7ff39e-7a4d-421d-92f3-987b9c411d33",
        "transactionDate": 1661091574279,
        "nominal": 1.03E7,
        "guaranteePicture": "/transactions/c3ef2298-2d8a-4818-984d-13315b78951f/files",
        "loanStatus": "PAID",
        "createdAt": 1661002579786,
        "updatedAt": 1661091574307
      }
    ],
    "createdAt": 1661106557370,
    "updatedAt": null
  }
}
```

#### Approve Transaction Request By Admin Id

Request :

- Authorize : `ADMIN/STAFF ONLY`
- Endpoint : ```/api/transactions/{adminId}/approve```
- Method : PUT
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body : ```CREATE DTO FOR DIFFERENT REQUEST```

```json
{
  "loanTransactionId": "9abfd33f-de4a-4d44-834d-26ba2860e7fd",
  "interestRates": 3
}
```

Response : ```CREATE DTO IF YOU WANT DIFFERENT RESPONSE```

```json
{
  "message": "string",
  "data": {
    "id": "bec5ca14-a867-4f34-a919-62dd6f941dec",
    "loanTypeId": "d781644b-a73f-4d54-ae94-a7b59bff05fc",
    "instalmentTypeId": "3e7023c8-77a8-4311-a195-3958a14a20a1",
    "customerId": "250b8bb1-7d55-458e-b30f-76c7307399bc",
    "nominal": 1.0E7,
    "approvedAt": 1661106557370,
    "approvedBy": "rifqyomp@gmail.com",
    "approvalStatus": "APPROVED",
    "transactionDetails": [
      {
        "id": "fd7ff39e-7a4d-421d-92f3-987b9c411d33",
        "transactionDate": 1661091574279,
        "nominal": 1.03E7,
        "guaranteePicture": "/transactions/c3ef2298-2d8a-4818-984d-13315b78951f/files",
        "loanStatus": "PAID",
        "createdAt": 1661002579786,
        "updatedAt": 1661091574307
      }
    ],
    "createdAt": 1661106557370,
    "updatedAt": 1661108557312
  }
}
```

#### Pay Instalment

Request :

- Endpoint : ```/api/transactions/{trxId}/pay```
- Method : PUT
- Header :
    - Content-type: "multipart/form-data"
      Request :

```
  guaranteePicture : file
```

Response :

```json
{
  "message": "string",
  "data": null
}
```

#### Download Guarantee Picture

Request :

- Endpoint : ```/api/transactions/{fileId}/files```
- Method : GET
- Header :
    - "content-disposition"

Response :
```Response Image```
