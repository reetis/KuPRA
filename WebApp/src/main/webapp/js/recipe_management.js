+function($) {
    $('#public_access').click(function(event) {
        console.log("BBBBBBBBBBB");
        $('#public_access_value').val('1');
    });

    $('#private_access').click(function(event) {
        console.log("AAAAAAAAAA");
        $('#public_access_value').val('0');
    });

}(jQuery);