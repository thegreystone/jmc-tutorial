This application shows off the Management Console. It's especially useful for 
building triggers on CPU load and for illustrating how deadlocked threads will
show up in the Threads tab.

1. Can you build a rule that triggers and generates an alert?
2. What threads are deadlocked? Which thread is holding the lock that Thread-1 is waiting on?

Tips below if you get stuck:







































1. Triggers rules are built on the MBeans | Triggers tab.

2. The CPU load attribute is under oracle.jrockit.management/Runtime#CPULoad.

3. CPU load is in fractions, in other words 0.4 is 40%.

4. You need to enable the rule after creating it.

5. Deadlock detection is in the Runtime | Threads tab, and is enabled by checking the appropriate box.

6. You can sort on deadlocked threads by clicking the appropriate column header.

7. Deadlocked threads also have a different icon.

8. You can enable more columns, such as the _Lock Owner Name_, by clicking the table settings.

