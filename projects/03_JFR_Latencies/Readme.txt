This example features a logger that is shared between a bunch of worker threads.
Take a look at the pre-recorded latency_before.jfr file.

1. The idea with the worker threads is to get work done in parallel. How many 
   threads are actually running in parallel? Why?
   
2. Can you change the application by simply removing a single key word in one of the files to improve things?

3. Can you think of other ways to change the application to accomplish this?

Help below if you get stuck.






















































1. Look at the latency_before.jfr recording and check the automated analysis. What is flagged?

2. What class is all of the contention on? (Jump to the page associated with the higest ranked rules - Lock Instances.)

3. The blocking events all seem to be due to calls to the Log method.

4. There are a few ways to fix this available to us. We can cut the call to the logger altogether. If the logging doesn't use a shared resource, 
   we can just remove the synchronization from the log method. We could also provide each thread with its own logger, carefully making sure that 
   we do not end up blocking on another shared resource instead. Of course, this is just an example, and no such limitations exist - any of these
   solutions would do.

5. The latency_fixed.jfr recording shows the situation after the fix. The problem is no longer flagged. For a nice visual look at the difference, follow the lab instructions.