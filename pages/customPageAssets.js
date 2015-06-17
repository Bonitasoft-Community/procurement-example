'use strict';
(function() {

angular.module('pb.generator').filter('checkmark', function() {
	return function(input) {
		if (angular.isUndefined(input) || input == null)
			return '';
		return input ? '\u2713' : '\u2718';
	};
});

})();