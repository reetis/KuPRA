+function($) {
    $(document).ready(function() {
        $.material.init();

        $("body").tooltip({ selector: '[data-toggle=tooltip]' });

        $('.btn-file :file').on('fileselect', function (event, numFiles, label) {
            var input = $(this).parents('.input-group').find(':text'), log = numFiles > 1 ? numFiles + ' files selected' : label;
            if (input.length) {
                input.val(log);
            } else {
                if (log)
                    alert(log);
            }
        });
    });

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


    $(document).on('change', '.btn-file :file', function () {
        var input = $(this), numFiles = input.get(0).files ? input.get(0).files.length : 1, label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [
            numFiles,
            label
        ]);
    });

    // Load is used to ensure all images have been loaded, impossible with document

    $(window).load(function () {



        // Takes the gutter width from the bottom margin of .post

        var gutter = parseInt($('.post').css('marginBottom'));
        var container = $('#posts');



        // Creates an instance of Masonry on #posts

        container.masonry({
            gutter: gutter,
            itemSelector: '.post',
            columnWidth: '.post'
        });



        // This code fires every time a user resizes the screen and only affects .post elements
        // whose parent class isn't .container. Triggers resize first so nothing looks weird.

        $(window).bind('resize', function () {
            if (!$('#posts').parent().hasClass('container')) {



                // Resets all widths to 'auto' to sterilize calculations

                post_width = $('.post').width() + gutter;
                jQuery('#posts, body > #grid').css('width', 'auto');



                // Calculates how many .post elements will actually fit per row. Could this code be cleaner?

                posts_per_row = $('#posts').innerWidth() / post_width;
                floor_posts_width = (Math.floor(posts_per_row) * post_width) - gutter;
                ceil_posts_width = (Math.ceil(posts_per_row) * post_width) - gutter;
                posts_width = (ceil_posts_width > $('#posts').innerWidth()) ? floor_posts_width : ceil_posts_width;
                if (posts_width == jQuery('.post').width()) {
                    posts_width = '100%';
                }



                // Ensures that all top-level elements have equal width and stay centered

                jQuery('#posts, #grid').css('width', posts_width);
                jQuery('#grid').css({'margin': '0 auto'});



            }
        }).trigger('resize');



    });
}(jQuery);


   
