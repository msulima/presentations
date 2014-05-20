var socket = new WebSocket("ws://localhost:9000/socket");

var presentationsApp = angular.module('presentationsApp', [
  'ngRoute',
  'ngTouch'
]);

presentationsApp.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
  $locationProvider.html5Mode(true);

  $routeProvider
  .when('/slides/:slideId', {
    templateUrl: '/assets/views/slide.html',
    controller: 'SlideController',
    resolve: {
      slide: function ($route, Slides) {
        console.log($route.current);
        return Slides.get($route.current.params.slideId);
      }
    }
  })
  .otherwise({
    redirectTo:'/slides/title'
  });
}]);

var i = 0;

presentationsApp.controller('SlideController', ['$scope', '$document', '$location', '$sce', 'slide',
    function ($scope, $document, $location, $sce, slide) {
  var LEFT = 37;
  var RIGHT = 39;
  var SLIDE_CHANGED = /slideChanged: (.+)/;
  console.log(i);

  $scope.content = $sce.trustAsHtml(slide.data.content);

  socket.onmessage = function(msg) {
    console.debug("Got message: ", msg);

    if (/slideChanged: (.+)/.test(msg.data)) {
      $scope.$apply(function () {
        $scope.moveTo(SLIDE_CHANGED.exec(msg.data)[1], true);
      });
    }
}

  var onKeydown = function(event) {
    $scope.$apply(function () {
      if (event.keyCode === LEFT) {
        $scope.movePrevious();
        event.preventDefault();
      } else if (event.keyCode === RIGHT) {
        $scope.moveNext();
        event.preventDefault();
      }
    });
  }

  $scope.movePrevious = function() {
    if (slide.data.prev) {
      $scope.moveTo("/slides/" + slide.data.prev);
    }
  }

  $scope.moveNext = function() {
    if (slide.data.next) {
      $scope.moveTo("/slides/" + slide.data.next);
    }
  }

  $scope.moveTo = function(slide, ommitWebSocketMessage) {
    if (!(ommitWebSocketMessage === true)) {
      console.log("sendingMessage: ", "slideChanged: " + slide);
      socket.send("slideChanged: " + slide);
    }
    console.log("location: ", slide);
    $location.path(slide).url();
  }

  $document.bind('keydown', onKeydown);

  $scope.$on('$destroy', function() {
    $document.unbind('keydown');
  });
}]);

presentationsApp.factory('Slides', function($http) {
  return {
    get: function (slideId) {
      return $http.get('http://localhost:9000/slides/' + slideId);
    }
  };
});
