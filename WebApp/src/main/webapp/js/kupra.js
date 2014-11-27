+function($) {
    $(".linkImage").hover(function() {
        $(this).animate({
            opacity: 0.5
        }, 100);
    }, function() {
        $(this).stop(true, true).animate({
            opacity: 1
        }, 100);
    });
    $('.btn-toggle').click(function(event) {
        event.preventDefault();
        $(this).find('.btn').toggleClass('active');
        console.log("RANDOM");
        if ($(this).find('.btn-primary').size()>0) {
            $(this).find('.btn').toggleClass('btn-primary');
        }
        if ($(this).find('.btn-danger').size()>0) {
            $(this).find('.btn').toggleClass('btn-danger');
        }
        if ($(this).find('.btn-success').size()>0) {
            $(this).find('.btn').toggleClass('btn-success');
        }
        if ($(this).find('.btn-info').size()>0) {
            $(this).find('.btn').toggleClass('btn-info');
        }

        $(this).find('.btn').toggleClass('btn-default');

    });
    $(document).ready(function() {
        $("body").tooltip({ selector: '[data-toggle=tooltip]' });
    });
}(jQuery);


   
