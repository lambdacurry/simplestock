This is a fun Java REST API (apache cxf), which takes current stock exchange rates from Central German Bank (Bundesbank). 

It could be automatically deployed, if the deployment environment supports Java 11 (could be some problems with e.g. tomEE since it supports out-of-box only Java 8).

If it's not the case, there is a simple jetty server, which allows to deploy it locally on 8090 port.

Service is compiled and built using maven and requires >= Java 11.

Covered user stories:
    
    • get list of all available currencies
    • get all EUR exchange rates for the chosen date
    • get EUR exhange rate for a particular currency and the chosen date
    • get a conversion of certain amount of a chosen currency to EUR with a valid rate for the chosen date
