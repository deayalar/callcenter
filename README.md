# Call Center

To run this exercise please execute `mvn clean install`, you will see the unit tests performing concurrent calls in different scenarios. 
This project fullfills the requirements described here [backend test](https://drive.google.com/file/d/0BxHwiBJ3YfxVeWcwcWhBT0l4dDFQaW1iLXpYTloyeGRzX3JV/view?usp=sharing).

This solution is documented in:
 [Class Diagram](https://raw.githubusercontent.com/deayalar/callcenter/master/class%20diagram.png).

Solution to extras
1. When there are no available employees - The calls are assigned to the next available employee, there is no a queue but the dipatcher is still asking for the next available employee to assign the call,
the chain of responsability of this implementation describes that the next role in charge of a call that could not be processed by any director is assigned to operators and the flow continues, it is a circular chain of assignation

2. What to do if it receives more than ten calls - Since all the employees will be busy attending the first ten calls, the approach is the same used in the previous point.

3. Add unit tests if needed:
  - Populate staff map
  - Perform 10 concurrent calls
  - Receive more calls than capacity
  - Receive the same number of calls than number of operators (Should be assigned only to operators)
  - Receive calls for all operators and one of next level which is supposed to be supervisor
  - Assign calls for all operators and supervisors (All directors should be free)
  
