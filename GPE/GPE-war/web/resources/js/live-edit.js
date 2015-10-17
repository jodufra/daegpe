
if (typeof jQuery === 'undefined') { throw new Error('LiveEdit requires jQuery'); }

+function ($) {
    'use strict';

    var plugin_name = "live-edit";
    var roles = { show: 'show', hide: 'hide', save: 'save', input: 'input' };

    var d_hide = '[data-role="' + roles.hide + '"]';
    var d_save = '[data-role="' + roles.save + '"]';
    var d_input = '[data-role="' + roles.input + '"]';

    var methods = {
        show: function (template, options, pluginId, target) {
            var item = template;
            item = item.replace('{pluginId}', pluginId);

            $.each(options, function (key, value) {
                var typeo = typeof this;
                if (typeo == "string" || typeo == "number" || typeo == "boolean")
                    item = item.replace('{' + key + '}', value);
            });

            target.css("display", "none");
            target.after(item);
        },

        hide: function (element, options, pluginId, target) {
            if (options.alwaysVisible) return;

            var id = element.attr("id");
            $("#" + pluginId).remove();

            target.css("display", "");
            target.removeUniqueId();

            options.hidden();
        },

        save: function (element, options, pluginId, input, target) {
            var value = input.val();
            var form = $("#" + pluginId + " form");

            if (value == options.inputValue)
                return options.hide(element, options, pluginId, target);

            var data = serializeObject(form);

            var ajax = $.ajax({ type: options.actionType, url: options.actionUrl, data: data });

            ajax.done(function (result) {
                options.done(result, element, options, pluginId, input, target);
                options.saved(result);
            });

            ajax.fail(function (jqXHR, textStatus, errorThrown) {
                options.fail(pluginId, jqXHR.status, errorThrown);
            });

            return false;
        },

        done: function (result, element, options, pluginId, input, target) {
            if (!result.Error) {
                options.success(result, element, options, pluginId, input, target);
            }
            else {
                options.error(result, pluginId);
            }
        },

        success: function (result, element, options, pluginId, input, target) {
            setValidationState("#" + pluginId, "success");
            //showPopover($("#" + pluginId), result.Messages[0], [], "success");
            showGrowl("", result.Messages, "success");
            if (options.updateElementOnSave)
                updateElementOnSave(element, input.val(), options);

            options.hide(element, options, pluginId, target);
        },

        error: function (result, pluginId) {
            setValidationState("#" + pluginId, "warning");
            //showPopover($("#" + pluginId), "Ocurreu um erro", result.Messages, "danger");
            showGrowl("Ocurreu um erro", result.Messages, "danger");
        },

        fail: function (pluginId, status, errorThrown) {
            setValidationState("#" + pluginId, "error");
            //showPopover($("#" + pluginId), "Erro de Servidor", [status + " - " + errorThrown], "danger");
            showGrowl("Erro de Servidor", [status + " - " + errorThrown], "danger");
        }
    };

    function setValidationState(target, validationState) {
        var item = $(target);
        item.removeClass("has-success has-warning has-error");

        if (validationState != '') {
            item.addClass("has-" + validationState);
        }
    }


    function showGrowl(title, messages, state) {
        var content = "";
        $.each(messages, function () {
            var data = { title: title, message: this, location: "tc", size: "medium" };

            switch (state) {
                case "success":
                    $.growl.notice(data);
                    break;
                case "danger":
                    $.growl.error(data);
                    break;
                case "warning":
                    $.growl.warning(data);
                    break;
                default:
                    $.growl(data);
                    break;
            }
        });

    }

    function updateElementOnSave(element, value, options) {
        element.attr("data-input-value", value);
        if (value == null || value == "") {
            element.html(options.defaultEmpty);
            if (options.addEmptytWarning)
                element.addClass("text-warning");
        }
        else {
            element.html(value);
            if (options.addEmptytWarning)
                element.removeClass("text-warning");
        }
        options.inputValue = value;
    }

    function serializeObject(objectToSerialize) {
        var o = {};
        var a = objectToSerialize.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    }


    var LiveEdit = function (element, options) {
        this.element = $(element);
        this.element.uniqueId();
        this.pluginId = plugin_name + "-" + this.element.attr('id');
        this.options = $.extend({}, LiveEdit.DEFAULTS, options);

        var typeo = typeof options.target;
        if (options.target == null) {
            this.target = this.element;
        }
        else if (typeo === "object") {
            this.target = options.target;
        }
        else if (typeo === "string") {
            this.target = $(options.target);
        }

        this.element.on(options.showEvent, $.proxy(this.show, this));

    }

    LiveEdit.DEFAULTS =
    {
        role: roles.show,
        target: null,
        visible: false,
        alwaysVisible: false,
        addEmptytWarning: true,
        updateElementOnSave: true,
        showEvent: 'click',

        pluginStyle: '',
        pluginSize: 'default',
        actionType: "POST",
        actionUrl: "/",
        inputType: "text",
        inputName: 'name',
        inputValue: '',
        inputPlaceholder: '',
        inputAttrs: '',
        defaultEmpty: '',

        hiddenInputs: [],

        show: methods.show,
        shown: function (editor) { },
        hide: methods.hide,
        hidden: function () { },
        save: methods.save,
        done: methods.done,
        success: methods.success,
        error: methods.error,
        fail: methods.fail,
        saved: function (data) { },

        template: '<div id="{pluginId}"><form action="{actionUrl}" method="{actionType}"><div class="input-group {pluginSize}" style="{pluginStyle}"><span class="input-group-btn"><button class="btn btn-default text-danger" type="button" data-role="hide"><i class="fa fa-remove"></i></button></span><input type="{inputType}" name="{inputName}" data-role="input" value="{inputValue}" class="form-control" {inputAttrs} placeholder="{inputPlaceholder}"><span class="input-group-btn"><button class="btn btn-default text-success" type="submit" data-role="save"><i class="fa fa-save"></i></button></span></div></form></div>'
    }

    LiveEdit.prototype.show = function () {
        var $this = this.element;
        var options = this.options;
        var pluginId = this.pluginId;
        var target = this.target;

        options.show(options.template, options, pluginId, this.target);

        var form = $("#" + pluginId + " form");
        $.each(options.hiddenInputs, function (key, input) {
            if (this != null && typeof this == "object")
                form.prepend('<input type="hidden" name="' + input.name + '" value="' + input.value + '"/>')
        });

        form.on('submit', $.proxy(this.save, this));
        $("#" + pluginId + " " + d_save).on('click', $.proxy(this.save, this));
        $("#" + pluginId + " " + d_hide).on('click', $.proxy(this.hide, this));;
        $("#" + pluginId + " " + d_input).on("focus", function () { $(this).select(); });
        $("#" + pluginId + " " + d_input).keyup(function (e) {
            if (e.keyCode == 27) $("#" + pluginId + " " + d_hide).click();
        });
        $("#" + pluginId + " " + d_input).focus();

        options.shown($("#" + pluginId));

    }

    LiveEdit.prototype.hide = function (element, options, pluginId) {
        var options = this.options;
        var pluginId = this.pluginId;
        var input = $("#" + pluginId + " " + d_input);

        options.hide(this.element, options, pluginId, this.target);
    }

    LiveEdit.prototype.save = function () {
        var options = this.options;
        var pluginId = this.pluginId;
        var input = $("#" + pluginId + " " + d_input);

        return options.save(this.element, options, pluginId, input, this.target);
    }


    var old = $.fn.liveEdit;

    $.fn.liveEdit = function (option) {
        return this.each(function () {
            var $this = $(this);
            var data = $this.data('');
            var options = $.extend({}, LiveEdit.DEFAULTS, $this.data(), typeof option == 'object' && option);

            if (options.pluginSize == "default" || options.pluginSize == "normal" || options.pluginSize == "medium")
                options.pluginSize = "";
            if (options.pluginSize == "small" || options.pluginSize == "sm")
                options.pluginSize = "input-group-sm";
            if (options.pluginSize == "large" || options.pluginSize == "lg")
                options.pluginSize = "input-group-lg";

            if (!data) {
                $this.data('le', (data = new LiveEdit(this, options)));
            }
            if (typeof option == 'string') {
                data[option]();
            }
            else if (options.visible) {
                data.show();
            }
        });
    }

    $.fn.liveEdit.Constructor = LiveEdit;

    $.fn.liveEdit.noConflict = function () {
        $.fn.liveEdit = old;
        return this;
    }

}(jQuery);