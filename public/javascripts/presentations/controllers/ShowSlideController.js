function ShowSlideController($scope, $routeParams, $location, $http, ListTools, slide, currentSlideId, allSlidesCount) {
  $scope.content = slide.data;
  $scope.currentSlideId = currentSlideId;
  $scope.allSlidesCount = allSlidesCount;
  $scope.allSlides = ListTools.range(1, allSlidesCount);

  $scope.keypressCallback = function ($event) {
    if ($event.keyCode == 37 && $scope.currentSlideId > 1) {
      $scope.movePrevious();
      $event.preventDefault();
    } else if ($event.keyCode == 39 && $scope.currentSlideId < $scope.allSlidesCount) {
      $scope.moveNext();
      $event.preventDefault();
    }
  };

  $scope.movePrevious = function() {
      $scope.changeSlide($scope.currentSlideId - 1);
  }

  $scope.moveNext = function() {
      $scope.changeSlide($scope.currentSlideId + 1);
  }

  $scope.changeSlide = function (slideId) {
      $location.path('showSlide/' + slideId).replace();
  }
}

ShowSlideController.resolve = {
  slide: function(SlidesService, $route) {
    return SlidesService.getSlide($route.current.params.slideId)
  },
  currentSlideId: function($route) {
    return parseInt($route.current.params.slideId);
  },
  allSlidesCount: function(ListTools) {
    return 4;
  }
}