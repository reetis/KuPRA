var $j = jQuery.noConflict();

$j(function() {
    $j(".linkImage").hover(function() {
        $j(this).animate({
            opacity: 0.5
        }, 100);
    }, function() {
        $j(this).stop(true, true).animate({
            opacity: 1
        }, 100);
    });
});

$j(document).ready(function() {
    $j("body").tooltip({ selector: '[data-toggle=tooltip]' });
});