# Logic-redux

A Prolog like language that runs on the JVM.

## Syntax Differences with Prolog
* Atoms and complex terms can start with either an uppercase or lowercase letter.
* Variables are prefixed with '@' (i.e. @foo instead of Foo).

## In progress work (i.e. incomplete)
* Loading terms and rules from files is not functioning currently.  They must be loaded via the repl.

## Work that hasn't started yet
* There is no support for lists.
* There is no support for 'is'.
* Introspection of a knowledge space.
* Named knowledge spaces.

## Work that will likely not be supported
* There is not short circuiting of rules that will never terminate.
* There is no support for operators.
* There is no (and there is no plan to) support for input/output (Just use Java...).
* There is no (and there is no plan to) support for cut.

## How do I build it?

### To build via maven


#### Build logic-redux

* cd *dir*
* git clone https://github.com/brweber2/logic-redux.git
* cd logic-redux
* mvn clean install

## How do I run it?

### To run programmatically via Java

* Construct a knowledge base
* Add knowledge to the knowledge base
* Query the knowledge base
* A tutorial will be added at a later date, see the unit tests for examples today.

### To run via the repl

* java -jar target/logic-redux-1.0.jar

## What can I do in the REPL?

### mode().

* This toggles you back and forth between assert mode and query mode.
* Assert mode has '!-' as its prompt and query mode uses '?-' as its prompt.

### trace().

* Toggles tracing on/off.

### prompt().

* Toggles prompt for more results on/off.  Use ';' to get additional results when prompted.

### exit, quit or halt

* Exits the REPL

### Load a file

* load('path to file').

### Assert knowledge

#### Facts

* foo.
* 'foo bar baz'.
* 1234.45.
* complex_term(foo,@bar,10).
* parent(Dave,Jack).

#### Rules

* related(@X,@Y) :- parent(@X,@Y).
* related(@X,@Y) :- parent(@X,@Z), related(@Z,@Y).
* choice(@X,@Y) :- @X; @Y.

### Query the knowledge base

* foo.
* 'foo bar baz'.
* 1234.56.
* related(Jack,Jill).
* related(@X,Bill).

## Where can I learn more about Prolog?

http://www.learnprolognow.org/
