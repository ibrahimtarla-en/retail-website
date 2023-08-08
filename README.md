# The Retail Store Discounts

## Description

On a retail website, the following discounts apply:
1. If the user is has an Gold Card of the store, he gets a 30% discount
2. If the user has silver card of the store, he gets a 20% discount
3. If the user is an affiliate of the store, he gets a 10% discount
4. If the user has been a customer for over 2 years, he gets a 5% discount.
5. For every $200 on the bill, there would be a $ 5 discount (e.g. for $ 950, you get $ 20
   as a discount).
6. The percentage based discounts do not apply on phones.
7. A user can get only one of the percentage based discounts on a bill.

Write a program in java such that given a bill, it finds the net payable amount. User interface is not required. What we care about:

object-oriented programming approach, provide a high level UML class diagram of all the key classes in your solution. This can either be on paper or using a CASE tool
1. Code to be generic and simple
2. Leverage today's best coding practices
3. Clear README.md that explains how the code and the test can be run and how the
   coverage reports can be generated


## Installing

After checking out the git repo run the following maven command

```bash
mvn clean install
```

This should install all packages, run all unit tests

## Starting

To start the server please run the following maven command

```bash
mvn spring-boot:run
```


## Testing

To execute the unit tests against the business logic service classes please run the following maven command

```bash
mvn test
```

## Code coverage

The code coverage report:


| Package     | Coverage      |
|-------------|---------------|
| all classes | 75% (9/12)    |
| method      | 71,1% (27/38) |
| line        | 83,1% (59/71) | 


## Using

### API Endpoint

* Http Method - **POST**
* Endpoint - **localhost:8080/api/v1/discounts**

Example request

```json
{
    "user": {
        "type": "EMPLOYEE",
        "registerDate": "2018-05-10",
        "card": {
          "type": "GOLD"
        }
    },
    "bill": {
        "productList": [
            {
                "type": "PHONE",
                "price": 200
            },
            {
                "type": "TECHNOLOGY",
                "price": 500
            },
            {
                "type": "OTHER",
                "price": 300
            }
        ]
    }
}

```

The response is net payable amount.

```json
740.0
```
