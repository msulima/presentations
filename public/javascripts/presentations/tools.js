
angular.module('presentationsTools', []).
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
  }).
  factory("UiTools", function($document, $parse) {
    return {
      captureKeys: function(scope, element, attrs) {
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
      }
    };
  });
