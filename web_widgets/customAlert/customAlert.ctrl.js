function WidgetalertController($scope, $sce) {
    
    this.getMessage = function () {
        var message = $scope.properties.message;
        if (angular.isObject(message) && message.message) {
            message = message.message;
        }
        return $sce.trustAsHtml(message);
    };
    
    this.getClasses = function () {
        var classes = 'alert '+ $scope.properties.style;
        if ($scope.properties.isDismissible)
            classes += ' alert-dismissible';
        return classes;
    };
    
    this.dismiss = function() {
        $scope.properties.message = null;
    };
}