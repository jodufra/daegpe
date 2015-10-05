var App = new (function () {
    var appCtrl = this;

    var body;

    appCtrl.Init = function (onComplete) {
        body = $("body");
        appCtrl.InitResizableContent();
        appCtrl.StartTabs();
        //appCtrl.StartSliders();
        //appCtrl.SelectorFields();
        appCtrl.Tooltips();

        if (onComplete != null)
            onComplete();
    };

    appCtrl.InitResizableContent = function() {
        $(window).resize(function(){
            ResizePageContent();
        });
        ResizePageContent();
    };

    function ResizePageContent(){
        var bodyH = body.scrollHeight;
        var windowH = $(window).height();
        var navbarH = $("body > nav.navbar").outerHeight();

        var height;
        if(bodyH > windowH){
            height = bodyH - navbarH;
        }else {
            height = windowH - navbarH;
            body.css("min-height", height);
        }

        $("body > .site").css("min-height", height);
        $("body > .site > .sidebar-wrapper").css("min-height", height);
        $("body > .site > .content-wrapper").css("min-height", height);
    }

    appCtrl.StartTabs = function () {
        // Bootstrap Plugin
        if (body.find('*[data-plugin="tabs"]').length) {
            body.find('*[data-plugin="tabs"]').each(function () {
                $(this).click(function (e) {
                    e.preventDefault();
                    if (!$(this).parent().hasClass('disabled'))
                        $(this).tab('show');
                    return false;
                });
            });
        }
    };

    appCtrl.StartSliders = function () {
        // Flexslider Plugin
        if (body.find('*[data-plugin="flexslider"]').length) {
            body.find('*[data-plugin="flexslider"]').each(function () {
                $(this).flexslider({
                    animation: 'slide',
                    controlNav: true,
                    animationLoop: false,
                    slideshow: false,
                });
            });
        }

        if (body.find('*[data-plugin="flexslider-with-thumbnails"]').length) {
            $('*[data-plugin="flexslider-with-thumbnails"]').flexslider({
                animation: "slide",
                controlNav: "thumbnails",
                prevText: "",
                nextText: "",
            });
        }
    };

    appCtrl.SelectorFields = function () {

        function pictureFormater(state) {
            var originalOption = state.body;
            if ($(originalOption).data('photo') == undefined) return;
            return '<img class="flag" src="' + $(originalOption).data('photo') + '" /> ' + state.text;
        }

        // Select2 plugin 
        if (body.find('select[data-plugin="selector"]').length) {
            body.find('select[data-plugin="selector"]').each(function () {

                var options =
                {
                    allowClear: true,
                    minimumResultsForSearch: ($(this).attr('data-selector-search') != undefined && $(this).attr('data-selector-search') == 'no') ? -1 : 0
                };

                if ($(this).attr('data-selector-picture') != undefined && $(this).attr('data-selector-picture') == 'yes') {
                    var config =
                    {
                        formatResult: pictureFormater,
                        formatSelection: pictureFormater,
                        escapeMarkup: function (m) { return m; }
                    }
                    options = $.extend({}, options, config);
                }
                $(this).select2(options);
            });
        }

        //Selectize
        body.find('[data-plugin="selectize"]').selectize({
            create: false,
            sortField: {
                field: 'text',
                direction: 'asc'
            },
            dropdownParent: 'body'
        });

        body.find('[data-plugin="multiple-selectize"]').selectize();
    }

    appCtrl.Tooltips = function () {
        // Bootstrap Tooltips
        if (body.find('*[data-plugin*="tooltip"]').length) {
            body.find('*[data-plugin*="tooltip"]').each(function () {
                $(this).tooltip();
            });
        }
    }

    appCtrl.Init();
})();
