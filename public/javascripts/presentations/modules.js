
angular.module('presentations', ['slidesServices', 'tools']).
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
  }).directive('captureKeys', function($document, $parse) {
    return function(scope, element, attrs) {
      var expression = $parse(attrs['captureKeys']);
      var f = function(e){
        scope.$apply(function () {
          expression(scope, { '$event': event });
        });
      }
      $document.bind('keydown', f);
      scope.$on('$destroy', function() {
        $document.unbind('keydown', f)
      });
    };
  });

angular.module('slidesServices', []).
  factory('SlidesService', function($http) {
    return {
      getSlide: function (slideId) {
        return $http.get('/slideContent/' + slideId);
      }
    };
  });


angular.module('tools', []).
  factory('ListTools', function() {
    return {
      range: function (start, stop) {
        var result = []
        for (var i = start; i <= stop; i++) {
            result.push(i)
        }
        return result;
      }
    };
  });
  