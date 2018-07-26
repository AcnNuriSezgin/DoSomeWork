# DoSomeWork
Perform conditional expressions as a pipeline like that simple if-elseif-else cycle.

## Prerequisites
First, dependency must be added to build.gradle file.
```groovy
implementation 'nurisezgin.com.dosomework:dosomework:1.0.0'
```
## How To Use
There are kind of two execution type one of them SyncExecutor, other one of them AsyncExecutor. For sequential conditions and actions can use **that**, for asyncs should use **thatAsync**.

* SyncExecution with single condition and single action.

```java
    DoSomeWork.that(() -> "Value")
        .expect(StringUtil::shouldNotEmpty)
        .then(System.out::println)
        .done();
```

* SyncExecution with single condition, single action and otherwise.

```java
    DoSomeWork.that(() -> person)
        .expect(person -> person.age < 18)
        .then(person -> button.setText("You are young for playing any bet"))
        .otherwise(person -> button.setText("Play"));
```

* SyncExecution with single condition and multiple actions.

```java
    DoSomeWork.that(() -> "Value")
        .expect(StringUtil::shouldNotEmpty)
        .then(System.out::println)
        .then(str -> System.out.println("The string is not empty"))
        .done();
```

* SyncExecution with multiple conditions and multiple actions.

```java
    DoSomeWork.that(() -> "Value")
        .expect(StringUtil::shouldEmpty)
        .then(System.out::println)
        .then(str -> System.out.println("The string is empty"))
        .or(StringUtil::shouldNotEmpty)
        .then(str -> System.out.println("The string is not empty"))
        .done();
```

* SyncExecution with multiple conditions, multiple actions and otherwise expression.

```java
    DoSomeWork.that(() -> 10)
        .expect(num -> num > 5)
        .then(System.out::println)
        .then(str -> System.out.println("The number is greater than 5"))
        .or(num -> num > 7)
        .then(str -> System.out.println("The number is greater than 7"))
        .otherwise(str -> System.out.println("unknown number"));
```

* You can use AsyncExecution same like SyncExecutions. When need listen to pipeline end can you **doOnEnd** method, that method requires a runnable object. It will be doing after pipeline execute all conditions and actions. **Terminable** object give a chance for terminating all pipeline. 

```java
    Terminable terminable = DoSomeWork.thatAsync(() -> "Value")
            .expect(new TestAsyncPredicate(StringUtil::shouldEmpty))
            .then(new TestAsyncConsumer(str -> object.setId(1)))
            .or(new TestAsyncPredicate(str -> len(str) < 3))
            .then(new TestAsyncConsumer(str -> object.setId(11)))
            .then(new TestAsyncConsumer(str -> object.setId(12)))
            .then(new TestAsyncConsumer(str -> object.setId(13)))
            .doOnEnd(() -> System.out.println("Pipeline is end"))
            .otherwise(new TestAsyncConsumer(str -> object.setId(expected)));

    terminable.terminate();    
```

## Authors
* **Nuri SEZGIN**-[Email](acnnurisezgin@gmail.com)

## Licence
Copyright 2018 Nuri SEZGIN

Apache License 2.0