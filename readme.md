### **readme.md**
###### **JSON Library**
I chose to use the JSON-lib json library for multiple reasons. The first being
that the JSON-lib library is both widely adopted and updated often compared to 
other json libraries. This is illustrated clearly when comparing it to the jsonp 
library which had its last update in November 2008 and has just 23 uses. The 
documentation for JSON-lib is also robust. Build instructions, recent updates, 
method documentation, etc. is included. As there is a relatively large community 
who uses the library there are also plenty of resources online to assist with 
implentation.

The JSON-lib licence allows use for anyone without restrictions on use in 
software created for commercial purposes.

Stress testing showed that JSON-lib exhibited reliable performance when asked 
to deal with multiple LoggingEvent's, with an average time of 20 milliseconds 
for 1000 LoggingEvents.

JSON-lib has relatively few direct dependencies at just 3, this is good to 
minimise complexity. 

Online documentation indicates JSON-lib is especially efficient at dealing with 
large amounts of data. This makes the library a good choice as it accomodates 
upscaling.
