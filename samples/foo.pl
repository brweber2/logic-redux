bar(abc).
bar(def).
bar(ghi).
bar(jkl).
quux.

baz(X) :- quux; bar(X).

