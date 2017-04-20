# akka-http-example

Multi-user service that calculates special floating point coefficients. There are two modifiable files in the resources folder.
f1.csv contains input floating point numbers, f2.csv contains intermediate floating point numbers.

### Requests:

* *Get(v1)*: return v1-th result from f2 (starting from 0) and correct  
&nbsp;&nbsp;if (f2[v1] > 10)  
&nbsp;&nbsp;&nbsp;&nbsp;f2[v1] - 10  
&nbsp;&nbsp;else f2[v1]  

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Result is returned to client via JSON, input is in the URL

* *Post(v2, v3, v4)*: calculate following expression  
&nbsp;&nbsp;if (f1[v3] + v2) < 10  
&nbsp;&nbsp;&nbsp;&nbsp;f2[v4] = f1[v3] + v2 + 10  
&nbsp;&nbsp;else f2[v4] = f1[v3] + v2  

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Result is saved in f2. If condition is true return 0, else 1, both in JSON.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Input parameters are sent via JSON:  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {  "tuple" : [v2, v3, v4] }
