# ATA IT Backend Task

In this task, Spring Boot 3.1 with mysql is used.
For querying, spring boot data jdbc is used, because JDBC might be more suitable for this project where dynamic fields and attributes are required to query.

## About the project and filtering
Actually there is not much needed to explain, but since the salary data is not in the same uniform data formats, here is how I filtered the data.
First I thought of cleaning up and standardizing the data, but I thought the team might want me to filter the data as it is, so I just filterd data as it exists.

Users can search with min_salary, and max_salary, they are inclusive of the salary value, and because there are also other currency values, I also put a param called "currency".
The most frequent found values in salary fields are assumed USD in yearly rate.
If no currency is defined, or default currency (like "$" or "USD" is provided, then the query will seach for all the available data.

But if a currency, for example, Euro, is provided, then the query will search only for the rows that include the currency provided, and filter those result again with minimum salary and maximum salary.

As some records have use K (for example, 150K), I changed those formats to be the same as others (multiply with 1000) after currency filtering.

And after that, the results are compared,filtered and returned.

Some rare occurences, like Strings in the salary fields, or ranges (about four occurences), and double currency rows(for example, 1500000 USD/ 100K GBP) will not be able to get filterd by the salary field. (They could be filtered by job title or other fields).


## Inform
And currency field without max_salary or min_salary field will not work, as we are not filtering with salary, and sortType (ASC, DESC) field without sorting field will also not work,
They will just simply be ignored.


## Thank you
That is all I would like to explain here and I would like to thank the hiring team for this opportunity again form here :D
