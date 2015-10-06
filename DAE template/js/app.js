var Sidebar = new(function () {
    var sidebarCtrl = this;
    var sidebar = $("body .sidebar");

    sidebarCtrl.init = function () {
        $(".toggle", sidebar).click(function (e) {
            e.preventDefault();
            var parent = $(this).parent();
            var nestedUl = $("ul", parent);
            var liCount = $("li", nestedUl).length;


            if (parent.hasClass("open")) {
                nestedUl.css("height", "0");
            } else {
                var liCount = $("li", nestedUl).length;
                nestedUl.css("height", 46 * liCount);
            }

            parent.toggleClass("open");

        });
    };

    return sidebarCtrl;
})();

var Content = new(function () {
    var contentCtrl = this;
    var element;

    contentCtrl.init = function (elem, onComplete) {
        element = elem;
        contentCtrl.initTabs();
        contentCtrl.initTooltips();

        if (typeof onComplete !== "undefined")
            onComplete();
    }

    contentCtrl.initTabs = function () {
        // Bootstrap Plugin
        if (element.find('*[data-plugin="tabs"]').length) {
            element.find('*[data-plugin="tabs"]').each(function () {
                $(this).click(function (e) {
                    e.preventDefault();
                    if (!$(this).parent().hasClass('disabled'))
                        $(this).tab('show');
                    return false;
                });
            });
        }
    };

    contentCtrl.initSliders = function () {
        // Flexslider Plugin
        if (element.find('*[data-plugin="flexslider"]').length) {
            element.find('*[data-plugin="flexslider"]').each(function () {
                $(this).flexslider({
                    animation: 'slide',
                    controlNav: true,
                    animationLoop: false,
                    slideshow: false,
                });
            });
        }

        if (element.find('*[data-plugin="flexslider-with-thumbnails"]').length) {
            $('*[data-plugin="flexslider-with-thumbnails"]').flexslider({
                animation: "slide",
                controlNav: "thumbnails",
                prevText: "",
                nextText: "",
            });
        }
    };

    contentCtrl.initSelectors = function () {
        function pictureFormater(state) {
            var originalOption = state.body;
            if ($(originalOption).data('photo') == undefined) return;
            return '<img class="flag" src="' + $(originalOption).data('photo') + '" /> ' + state.text;
        }

        // Select2 plugin 
        if (element.find('select[data-plugin="selector"]').length) {
            element.find('select[data-plugin="selector"]').each(function () {

                var options = {
                    allowClear: true,
                    minimumResultsForSearch: ($(this).attr('data-selector-search') != undefined && $(this).attr('data-selector-search') == 'no') ? -1 : 0
                };

                if ($(this).attr('data-selector-picture') != undefined && $(this).attr('data-selector-picture') == 'yes') {
                    var config = {
                        formatResult: pictureFormater,
                        formatSelection: pictureFormater,
                        escapeMarkup: function (m) {
                            return m;
                        }
                    }
                    options = $.extend({}, options, config);
                }
                $(this).select2(options);
            });
        }

        //Selectize
        element.find('[data-plugin="selectize"]').selectize({
            create: false,
            sortField: {
                field: 'text',
                direction: 'asc'
            },
            dropdownParent: 'body'
        });

        element.find('[data-plugin="multiple-selectize"]').selectize();
    }

    contentCtrl.initTooltips = function () {
        // Bootstrap Tooltips
        if (element.find('*[data-plugin*="tooltip"]').length) {
            element.find('*[data-plugin*="tooltip"]').each(function () {
                $(this).tooltip();
            });
        }
    }

    return contentCtrl;
})();

var App = new(function () {
    var appCtrl = this;

    appCtrl.init = function () {
        appCtrl.initResizableSite();
        Sidebar.init();
        Content.init($("body"));
    };

    appCtrl.initResizableSite = function () {
        var resizePageSite = function () {
            var body = $("body");
            var navbar = $("body > nav.navbar");
            var site = $("body > .site");
            var sidebar = $("body > .site > .sidebar-wrapper");
            var content = $("body > .site > .content-wrapper");

            body.css("height", "");
            site.css("height", "");
            sidebar.css("height", "");
            content.css("height", "");

            var bodyH = body[0].scrollHeight;
            var windowH = $(window).height();
            var navbarH = navbar.outerHeight();
            var sidebarH = sidebar.outerHeight()

            var height;
            if (bodyH > windowH) height = bodyH;
            else height = windowH;
            if (sidebarH + navbarH > height) height = sidebarH + navbarH;

            body.css("height", height);
            height -= navbarH;
            site.css("height", height);
            sidebar.css("height", height);
            content.css("height", height);
        }

        $(window).resize(function () {
            resizePageSite();
        });
        resizePageSite();
    };

})();

$(function () {
    App.init();
});
