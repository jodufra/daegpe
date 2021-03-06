var Sidebar = new (function () {
    var sidebarCtrl = this;
    var sidebar = $("body .sidebar");

    sidebarCtrl.init = function () {
        var open = function (elem) {
            var parent = $(elem).parent();
            var nestedUl = $("ul", parent);
            var liCount = $("li", nestedUl).length;


            if (parent.hasClass("open")) {
                nestedUl.css("height", "0");
            } else {
                liCount = $("li", nestedUl).length;
                nestedUl.css("height", 46 * liCount);
            }

            parent.toggleClass("open");
        }


        var value = $('input[name="active-sidebar-option"]', sidebar).val();
        $("#sidebar-options > li > a:contains(" + value + ")").parent().addClass("active");
        $("#sidebar-options > li.active .toggle").each(function () {
            open(this);
        });
        $(".toggle", sidebar).click(function (e) {
            e.preventDefault();
            open(this);
        });
    };

    return sidebarCtrl;
})();

var Content = new (function () {
    var contentCtrl = this;
    var element;

    contentCtrl.init = function (elem, onComplete) {
        element = elem;
        contentCtrl.initTabs();
        contentCtrl.initTooltips();
        contentCtrl.initLiveEdit();
        contentCtrl.initDatepickers();
        contentCtrl.initTimepickers();
        contentCtrl.initDatetimepickers();
        contentCtrl.initSelectize();

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
            if ($(originalOption).data('photo') == undefined)
                return;
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
    };

    contentCtrl.initTooltips = function () {
        // Bootstrap Tooltips
        if (element.find('*[data-plugin*="tooltip"]').length) {
            element.find('*[data-plugin*="tooltip"]').each(function () {
                $(this).tooltip();
            });
        }
    };

    contentCtrl.initLiveEdit = function () {
        if (element.find('*[data-plugin*="live-edit"]').length) {
            element.find('*[data-plugin*="live-edit"]').each(function () {
                $(this).liveEdit();
            });
        }
    };

    contentCtrl.initDatepickers = function () {
        var elements = element.find('.datepicker');
        if (elements.length) {
            elements.each(function () {
                $(this).datepicker({
                    format: "dd/mm/yyyy",
                    language: "pt",
                    calendarWeeks: true,
                    autoclose: true,
                    todayHighlight: true
                });
            });
        }
    };

    contentCtrl.initTimepickers = function () {
        var elements = element.find('.timepicker');
        if (elements.length) {
            elements.each(function () {
                $(this).timepicker({'timeFormat': 'H:i'});
            });
        }
    };

    contentCtrl.initDatetimepickers = function () {
        var elements = element.find('.datetimepicker');
        if (elements.length) {
            elements.each(function () {
                $(this).datetimepicker({
                    locale: "pt",
                });
            });
        }
    };

    contentCtrl.initSelectize = function () {
        var elements = element.find('.selectize');
        if (elements.length) {
            elements.each(function () {
                $(this).selectize({
                    plugins: ['restore_on_backspace'],
                    delimiter: ',',
                    persist: false,
                    create: function (input) {
                        return {
                            value: input,
                            text: input
                        }
                    }
                });
            });
        }
    };

    return contentCtrl;
})();

var App = new (function () {
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
            if (bodyH > windowH)
                height = bodyH;
            else
                height = windowH;
            if (sidebarH + navbarH > height)
                height = sidebarH + navbarH;

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
    $('[data-toggle="tooltip"]').tooltip()


});
