+function($) {
    $j('#public_access').click(function(event) {
        console.log("BBBBBBBBBBB");
        $j('#public_access_value').val('1');
    });

    $j('#private_access').click(function(event) {
        console.log("AAAAAAAAAA");
        $j('#public_access_value').val('0');
    });

}(jQuery);