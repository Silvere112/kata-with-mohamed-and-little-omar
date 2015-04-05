var decisionProjection = require('../decisionProjection');
var valueType = require('../../valueType');
var session = require('./session');

var UserIdentityId = exports.UserIdentityId = valueType.extends(function UserIdentityId(email){
    this.email = email;
}, function toString(){
    return 'UserIdentity:' + this.email;
});

var UserRegistered = exports.UserRegistered = function UserRegistered(userIdentityId){
    this.userIdentityId = userIdentityId;

    Object.freeze(this);
};

var UserIdentity = function UserIdentity(events){
    var self = this;

    var projection = decisionProjection.create().register(UserRegistered, function(event) {
        this.id = event.userIdentityId;
    }).apply(events);

    self.logIn = function logIn(publishEvent){
        return session.logIn(publishEvent, projection.id);
    };
};

exports.register = function register(publishEvent, email){
    var id = new UserIdentityId(email);
    publishEvent(new UserRegistered(id));
};

exports.create = function create(events){
    return new UserIdentity(events);
};