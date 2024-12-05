# Contact API

This is a Spring Boot REST API for managing contacts.

## Features

- Create, retrieve, update, and delete contacts
- upload contact image
- get contact image
- get image by imageName
- get all contacts
- get contact by id
- get contact by name


## Usage

### Create a new contact

```
POST /contact
Body:
{
    "name": "John Doe",
    "email": "OcKoB@example.com",
    "phone": "1234567890",
    "address": "123 Main St, Anytown, USA",
    "age": 30
}
```

### Get all contacts

```
GET /contact
```

### Get contact by id

```
GET /contact/{id}
```

### Update contact

```
PUT /contact/{id}
Body:
{
    "name": "John Doe",
    "email": "OcKoB@example.com",
    "phone": "1234567890",
    "address": "123 Main St, Anytown, USA",
    "age": 30
}
```


### Upload contact image

```
POST /contact/{id}/image
Form Data:
{
    "image": <image file>
}
```


### Get contact image

```
GET /contact/{id}/image
```

### Delete contact

```
DELETE /contact/{id}
```