/**
 * Created by Yago on 09/04/2016.
 */
(function($) {
    "use strict";

    var $navbar = $("#navbar"),
        y_pos = $navbar.offset().top,
        height = $navbar.height();

    $(document).scroll(function() {
        var scrollTop = $(this).scrollTop();

        if (scrollTop > y_pos + height) {
            $navbar.addClass("navbar-sticky").animate({
                top: 0
            });
        } else if (scrollTop <= y_pos) {
            $navbar.removeClass("navbar-sticky").clearQueue().animate({
                top: "-48px"
            }, 0);
        }
    });

})(jQuery, undefined);