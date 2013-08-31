
angular.module('presentations', ['slidesServices', 'presentationsTools']).
  config(function($routeProvider) {
    $routeProvider.
      when('/showSlide/:slideId', {
        controller: ShowSlideController, 
        templateUrl: 'assets/templates/show-slide.html',
        resolve: ShowSlideController.resolve}).
      when('/viewPresentation/', {
        controller: ViewPresentationController, 
        templateUrl: 'assets/templates/view-presentation.html'}).
      otherwise({redirectTo:'/showSlide/1'});
  }).directive('captureKeys', function(UiTools) {
      return UiTools.captureKeys;
  });

angular.module('slidesServices', []).
  factory('SlidesService', function($http) {
    return {
      getSlide: function (slideId) {
        return $http.get('/slideContent/' + slideId);
      }
    };
  });
