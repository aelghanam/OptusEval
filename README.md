# OptusEval

This app is a Spring Boot app, packaged as a JAR.
There is a basic authentication layer on top of the WSs, that uses Basic Auth with a username and pass (or just an Auth Header).
It has two webservices.

WS 1 : 

URL : 
  /content-api
Details:
  This WS accepts POST requests, with a JSON body. Please find below an example of the JSON body. The array of searchText contains words that will be used as search terms to search a txt file for the number of occurences of these words. Then it will return a JSON response with each word and the number of occurences in the file.
Example Request:
  {"searchText":["Duis", "Sed"]}
Example Response:
  {"counts":[{"Duis":11},{"Sed":16}]}
  
  
WS 2 :

URL :
  /top/{countReturn}
Details:
  This WS doesnt need any post body, but rather it takes the param as a path variable from the rest URL. The path variable passed is an integer that is used to identify the number of words that the WS should return that are the top occurences in the file.

Example Response:
  eget|17
  vel|17
  Sed|16
  sed|16
  In|15
  in|15
  et|14
  Ut|13
  eu|13
  ut|13
