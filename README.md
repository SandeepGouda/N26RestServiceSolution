# N26RestServiceSolution

The main use case for our API is to calculate realtime statistic from the last 60 seconds.
There will be two APIs, one of them is
called every time a transaction is made. It is also the sole input of this rest API. The other one
returns the statistic based of the transactions of the last 60 seconds

1.POST /transactions : Every Time a new transaction happened, this endpoint will be called.

2.GET /statistics : It returns the statistic based on the transactions which happened in the last 60 seconds.

Requirements
For the rest api, the biggest and maybe hardest requirement is to make the GET /statistics
execute in constant time and space. The best solution would be O(1). It is very recommended to
tackle the O(1) requirement as the last thing to do as it is not the only thing which will be rated in
the code challenge.
Other requirements, which are obvious, but also listed here explicitly:
● The API have to be threadsafe with concurrent requests
● The API have to function properly, with proper result
● The project should be buildable, and tests should also complete successfully. e.g. If
maven is used, then mvn clean install should complete successfully.
● The API should be able to deal with time discrepancy, which means, at any point of time,
we could receive a transaction which have a timestamp of the past
● Make sure to send an in memory solution without database, (including no in memory
database).
● Endpoints have to execute in constant time and memory (O(1))