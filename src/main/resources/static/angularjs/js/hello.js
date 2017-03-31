angular.module('hello', [])
  .controller('home', function($http) {
  var self = this;
  $http.get('/greeting').then(function(response) {
    self.greeting = response.data;
  })
});
