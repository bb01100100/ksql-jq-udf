# ksql-jq-udf
A simple user defined function (UDF) that wraps Jq, allowing a column containing JSON data to be filtered and transformed

## Installation
. Build the project with `./gradelw shadowJar` 
. Copy the resulting jar to the `/ext` folder in your KSQL Server home directory
. Restart the ksql-server process


## Testing availability
From a ksql client prompt, list the functions with `show functions`. You should see `COLTOJSON` in the list.
Read the meagre description of the function: 
```
ksql> describe function coltojson;

Name        : COLTOJSON
Overview    : Compose JSON output from a single column
Type        : scalar
Jar         : <path-to-your-ksql-server>/ext/KsqlJqUdf-1.0-SNAPSHOT-all.jar
Variations  : 

        Variation   : COLTOJSON(VARCHAR, VARCHAR)
        Returns     : VARCHAR
        Description : Take a single column and apply a Jq filter to it.
```

## Usage
Given a KSQL Stream that has a field containing a valid JSON object:

```
ksql> select * from s4 limit 1;
1561530541681 | 101 | {"address":{"number":"82A","street":"George St","suburb":"Ashtead"},"customerId":"101"}
Limit Reached
Query terminated
```

You can query/filter/transform the column using your familiar `jq` tool:

```
ksql> select coltojson(customer, '{\\"id\\": .customerId, \\"suburb\\": .address.suburb}') as customerinfo from s4 limit 1;
{"id":"101","suburb":"Ashtead"}
Limit Reached
Query terminated
```
