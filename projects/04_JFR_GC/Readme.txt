This example shows allocation behavior due to a suboptimal choice in datatypes.

1. Look at the allocation_before.jfr recording.
2. Go to the Garbage Collections page. Marvel at the frequency of garbage collections.
2. Look at the Memory page. What kind of object seems to be the one mostly allocated.
3. What is causing the allocations?
4. What is causing the garbage collections?
5. Can a simple change of data type in one of the classes help?
6. After fixing, how many garbage collections do you get?
7. What does the allocation behaviour look like?

Help below if you get stuck:























































(In JMC 7 the reason is shown directly in the automated analysis. Let's ignore that for a bit.)
1. Memory tab helps. TLAB page shows even more detail.
2. Integer allocations are abundant.
3. Trace shows that valueOf causes the allocations.
4. Allocator autoboxes int to Integer, causing the allocations.
5. Switching to Integer in the MyAlloc inner class will help (replace all int with Integer). Typically, 
   when primitive types are used as index in HashMaps, storing the object version is a better idea than 
   going back and forth between the primitive type and Object version.
6. After fixing, there are no garbage collections anymore. Check and compare Garbage Collections pages for the two recordings. 
7. Of course, in JMC 7 the problem is explained directly in the automated analysis results page...