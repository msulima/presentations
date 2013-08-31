function ViewPresentationController($scope, $http) {
  $scope.content = "";

  $scope.changeSlide = function (msg) { 
    $scope.$apply(function () { 
      $scope.content = JSON.parse(msg.data).data
    }); 
  };

  $scope.listen = function () {
      $scope.presentationFeed = new EventSource("/presentationFeed");
      $scope.presentationFeed.addEventListener("message", $scope.changeSlide, false);
  };

  $scope.listen();
}
