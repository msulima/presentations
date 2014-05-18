var socket = new WebSocket("ws://localhost:9000/socket");

var presentationsApp = angular.module('presentationsApp', [
  'ngRoute'
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
  var swipeElement = ".presentationArea-wrapper";
  console.log(i);

  $scope.content = $sce.trustAsHtml(slide.data.slide);

  $(function () {
    $(swipeElement).on("swipeleft", function (event) {
      i++;
      $scope.moveNext();
    });

    $(swipeElement).on("swiperight", function (event) {
      i++;
      $scope.movePrevious();
    });
  });

  socket.onmessage = function(msg) {
    console.debug("Got message: ", msg);

    if (/slideChanged: (.+)/.test(msg.data)) {
      $scope.moveTo(SLIDE_CHANGED.exec(msg.data)[1], true);
    }
}

  var onKeydown = function(event) {
    if (event.keyCode === LEFT) {
      $scope.movePrevious();
      event.preventDefault();
    } else if (event.keyCode === RIGHT) {
      $scope.moveNext();
      event.preventDefault();
    }
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
    $scope.$apply(function () {
      if (!(ommitWebSocketMessage === true)) {
        console.log("sendingMessage: ", "slideChanged: " + slide);
        socket.send("slideChanged: " + slide);
      }
      console.log("location: ", slide);
      $location.path(slide).url();
    });
  }

  $document.bind('keydown', onKeydown);

  $scope.$on('$destroy', function() {
    $(swipeElement).off('swipeleft');
    $(swipeElement).off('swiperight');
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
