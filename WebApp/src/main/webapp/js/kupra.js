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
    $j('.btn-toggle').click(function(event) {
        event.preventDefault();
        $j(this).find('.btn').toggleClass('active');
        console.log("RANDOM");
        if ($j(this).find('.btn-primary').size()>0) {
            $j(this).find('.btn').toggleClass('btn-primary');
        }
        if ($j(this).find('.btn-danger').size()>0) {
            $j(this).find('.btn').toggleClass('btn-danger');
        }
        if ($j(this).find('.btn-success').size()>0) {
            $j(this).find('.btn').toggleClass('btn-success');
        }
        if ($j(this).find('.btn-info').size()>0) {
            $j(this).find('.btn').toggleClass('btn-info');
        }

        $j(this).find('.btn').toggleClass('btn-default');

    });
});

$j(document).ready(function() {
    $j("body").tooltip({ selector: '[data-toggle=tooltip]' });
});
   
