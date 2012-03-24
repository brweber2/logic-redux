parent(Jill,Dave).
parent(Jack,Dave).
parent(Dave,Steve).
parent(Steve,Mike).
parent(Sally,Fred).

related(@X,@Y) :- parent(@X,@Y).
related(@X,@Y) :- parent(@X,@Z), related(@Z,@Y).
