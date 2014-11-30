+function($) {
    buttonField = $('.btn-toggle');
    hiddenField = $('#public_access_value');

    // Just In case Sets Default Value
    hiddenField.val('0');

    buttonField.click(function(event) {
        toogleInputValue();
    });
}(jQuery);

function toogleInputValue(){
    console.log(hiddenField.val())
    if (hiddenField.val() == '1'){
        hiddenField.val('0');
    } else {
        hiddenField.val('1');
    }
}