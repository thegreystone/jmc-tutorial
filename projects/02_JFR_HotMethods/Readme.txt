This example illustrates the hot methods tab in JRA and JFR. 

1. Make a recording or open the prepared recording (hotmethods_before.jfr) and check where most of the time is spent. 
2. Can you make the application run faster?

Hints below if you get stuck...













































Tip 1: You can make the program run a lot faster by simply changing _one_ line.

Tip 2: We spend a lot of time in the LinkedList.equals(Object)/LinkedList.indexOf(Object) method.

Tip 3: Looking at the trace to the hottest method, shows that we are passing through the contains method.

Tip 4: Contains in a linked list is proportional to the number of entries.

Tip 5: A HashSet will, on average, do the trick in constant time.

Tip 6: Find out where contains is being called from (Method Profiling page, look at what keeps calling the contains method).

Tip 7 (total spoiler, don't read this): Change line 28 in HolderOfUniqueValues.java to read: collection = new HashSet<Integer>(); Run and compare.

Tip 8: You can compare the time it takes to run the individual work units by looking at the work events, either by enabling the Thread activity lane in the Java Application page, or by looking at them in the Event Browser page. 