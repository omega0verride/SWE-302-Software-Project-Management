 exceptionId can be used by the client software to identify the exceptions  
its value matches the name of the class
some exceptions extend others, to be able to detect "superclass" exceptions
exceptionId will include <ex_class_0>.<ex_class_1>.<ex_class_{n}>

take for example InvalidTokenTypeException which extends InvalidTokenException
exceptionId=InvalidTokenException.InvalidTokenTypeException
this way you can check for InvalidTokenException and not worry about all the subclasses that derive from it (
InvalidTokenTypeException, InvalidTokenUserException etc.)
