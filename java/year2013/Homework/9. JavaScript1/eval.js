var cnst = function(x) {
    return function() {
        return x;
    }    
}

var variable = function(s) {
    if (s == "x") {
        return function(x, y, z) {
            return x;
        }  
    }

    if (s == "y") {
        return function(x, y, z) {
            return y;
        }  
    }

    if (s == "z") {
        return function(x, y, z) {
            return z;
        }  
    }
}

var subtract = function(a, b) {
    return function(x, y, z) {
        return a(x, y, z) - b(x, y, z);
    }
}

var abs = function(a) {
    return function(x, y, z) {
        return Math.abs(a(x, y, z));
    }
}

var log = function(a) {
    return function(x, y, z) {
        return Math.log(a(x, y, z));
    }
}

var negate = function(a) {
    return function(x, y, z) {
        return -(a(x, y, z));
    }
}


var multiply = function(a, b) {
    return function(x, y, z) {
        return a(x, y, z) * b(x, y, z);
    }
}

var add = function(a, b) {
    return function(x, y, z) {
        return a(x, y, z) + b(x, y, z);
    }
}

var divide = function(a, b) {
    return function(x, y, z) {
        return a(x, y, z) / b(x, y, z);
    }
}





