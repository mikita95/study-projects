/*Constant definition*/

function Const(a) {
    this.a = a;
}

var ZERO = new Const(0);
var ONE = new Const(1);

Const.prototype.toString = function() {
    return (this.a + " ");
}

Const.prototype.evaluate = function(x) {
    return this.a;
}

Const.prototype.diff = function(x) {
    return ZERO;
}

/*Variable definition*/

function Variable(tmp) {}

Variable.prototype.toString = function() {
    return "x ";
}

Variable.prototype.evaluate = function(x) {
    return x;
}

Variable.prototype.diff = function() {
    return ONE;
}

/*Binary definition*/

function Binary() {}

Binary.prototype.evaluate = function(x) {
    return this.run(this.a.evaluate(x), this.b.evaluate(x))
}

Binary.prototype.toString = function() {
    return (this.a.toString() + this.b.toString() + this.symbol + " ");
}

/*Unary definition*/

function Unary() {}

Unary.prototype.evaluate = function(value) {
    return this.run(this.a.evaluate(value))
}

Unary.prototype.toString = function() {
    return (this.a.toString() + this.symbol + " ");
}

/*Division definition*/

function Divide(a, b) {
    this.a = a;
    this.b = b;
}

Divide.prototype = new Binary();

Divide.prototype.symbol = "/";

Divide.prototype.run = function(a, b) {
    return a / b;
}

Divide.prototype.diff = function(x) {
    return new Divide(new Subtract(
                                   new Multiply(this.a.diff(), this.b), 
                                   new Multiply(this.a, this.b.diff())
                                   ), 
                      new Multiply(this.b, this.b)
                      );
}

/*Addition definition*/

function Add(a, b) {
    this.a = a;
    this.b = b;
}

Add.prototype = new Binary();

Add.prototype.symbol = "+";

Add.prototype.run = function(a, b) {
    return a + b;
}

Add.prototype.diff = function(x) {
    return new Add(
                   this.a.diff(), 
                   this.b.diff()
                   );
}

/*Multiplication definition*/

function Multiply(a, b) {
    this.a = a;
    this.b = b;
}

Multiply.prototype = new Binary();

Multiply.prototype.run = function(a, b) {
   return a * b;
}

Multiply.prototype.symbol = "*";

Multiply.prototype.diff = function(x) {
    return new Add(
                   new Multiply(this.a.diff(), this.b), 
                   new Multiply(this.a, this.b.diff())
                   );
}

/*Subtraction definition*/

function Subtract(a, b) {
    this.a = a;
    this.b = b;
}

Subtract.prototype = new Binary();

Subtract.prototype.symbol = "-";

Subtract.prototype.run = function(a, b) {
    return a - b;
}

Subtract.prototype.diff = function(x) {
    return new Subtract(
                        this.a.diff(), 
                        this.b.diff()
                        );
}

/*Sin definition*/

function Sin(a1) {
    this.a = a1;
}

Sin.prototype = new Unary();

Sin.prototype.run = function(x) {
    return Math.sin(x);
}

Sin.prototype.symbol = "sin";

Sin.prototype.diff = function(x) {
    return new Multiply(
                        this.a.diff(), 
                        new Cos(this.a)
                        );
}

function Cos(a1) {
    this.a = a1;
}

/*Cos definition*/

Cos.prototype = new Unary();

Cos.prototype.run = function(x) {
    return Math.cos(x);
}

Cos.prototype.symbol = "cos";

Cos.prototype.diff = function(x) {
    return new Multiply(
                        this.a.diff(), 
                        new Subtract(
                                     ZERO, 
                                     new Sin(this.a)
                                     )
                        );
}


