var $j = jQuery.noConflict();

$j(function() {
    $j(".linkImage").hover(function() {
        $j(this).animate({
            opacity: 0.5
        });
    }, function() {
        $j(this).stop(true, true).animate({
            opacity: 1
        });
    });
});