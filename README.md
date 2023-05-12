Got rid of unnecessary abstraction local provider
Moved movie schedule to be dependency injected into theater
Created a DiscountRule class to encapsulate a discount rule
Moved discount rules to be dependency injected into theater
Made discount rules a parameter of Showing.getMovieFee
Changed theater to pretty print discount rules applied to showings
Added jackson dependency for json serialization
Implemented additional feature requests for rules and json printing
Other minor improvements such as theater not requiring a date anymore
Added tests for all discount rules
