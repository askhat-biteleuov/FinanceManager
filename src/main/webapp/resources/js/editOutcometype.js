$(document).ready(function () {

    $('.editOutcometypeBtn').click(function (e) {
        e.preventDefault();
        $(this).closest('.editOutcometypeDiv').find(".acceptOutcometypeBtn").show();
        $(this).closest('.editOutcometypeDiv').find(".cancelOutcometypeBtn").show();
        $(this).hide();
        var outcometypeName = $(this).closest('.editOutcometypeDiv').find(".editOutcometypeName");
        outcometypeName.removeClass('ellipsis');
        var oldOutcometypeName = $(this).closest('.editOutcometypeDiv').find(".oldOutcometypeName");
        oldOutcometypeName.val(outcometypeName.text());
        outcometypeName.attr('contenteditable', 'true');
        outcometypeName.focus();

        var outcometypeLimit = $(this).closest('.editOutcometypeDiv').find(".editOutcometypeLimit");
        outcometypeLimit.show();
        var oldOutcometypeLimit = $(this).closest('.editOutcometypeDiv').find(".oldOutcometypeLimit");
        oldOutcometypeLimit.val(outcometypeLimit.text());
        outcometypeLimit.attr('contenteditable', 'true');

        $(this).closest('.editOutcometypeDiv').find(".defaultViewOfLimit").hide();
    });

    $('.acceptOutcometypeBtn').click(function (e) {
        e.preventDefault();
        var editDiv = $(this).closest('.editOutcometypeDiv');

        var outcometypeName = editDiv.find(".editOutcometypeName");
        var oldOutcometypeName = $.trim(editDiv.find(".oldOutcometypeName").val());
        var newOutcometypeName = outcometypeName.text().trim();

        var outcometypeLimit = editDiv.find(".editOutcometypeLimit");
        var oldOutcometypeLimit = $.trim(editDiv.find(".oldOutcometypeLimit").val());
        var newOutcometypeLimit = outcometypeLimit.text().trim();

        if (newOutcometypeName !== oldOutcometypeName || newOutcometypeLimit !== oldOutcometypeLimit) {
            var outcometypeId = $(this).closest('.editOutcometypeDiv').find(".outcometypeId").val();
            $.ajax({
                type: 'POST',
                beforeSend: function (request) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    request.setRequestHeader(header, token);
                },
                contentType: 'application/json; charset=UTF-8',
                url: "/outcometype/edit",
                data: JSON.stringify({
                    "id": outcometypeId,
                    "name": newOutcometypeName.trim(),
                    "limit": newOutcometypeLimit.trim()
                })
            }).done(function (data) {
                location.reload();
            }).fail(function (error) {
                var errors = error.responseJSON;
                var template = '<div class="popover alert-danger" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>';
                editDiv.find(".acceptOutcometypeBtn").popover({
                    placement: "bottom",
                    template: template,
                    trigger: "manual"
                });
                if (errors !== undefined) {
                    if (errors["name"] !== undefined && errors["limit"] !== undefined) {
                        editDiv.find(".acceptOutcometypeBtn").data('bs.popover').options.content = errors["name"] + "; " + errors["name"];
                    }
                    if (errors["name"] !== undefined) {
                        editDiv.find(".acceptOutcometypeBtn").data('bs.popover').options.content = errors["name"];
                    }
                    if (errors["limit"] !== undefined) {
                        editDiv.find(".acceptOutcometypeBtn").data('bs.popover').options.content = errors["limit"];
                    }
                }
                editDiv.find(".acceptOutcometypeBtn").popover('show');
            });
        } else {
            stopEdit(editDiv);
        }
    });

    $('.cancelOutcometypeBtn').click(function (e) {
        e.preventDefault();
        var editDiv = $(this).closest('.editOutcometypeDiv');
        var outcometypeName = editDiv.find(".editOutcometypeName");
        var oldOutcometypeName = editDiv.find(".oldOutcometypeName");
        outcometypeName.text(oldOutcometypeName.val());
        var outcometypeLimit = editDiv.find(".editOutcometypeLimit");
        var oldOutcometypeLimit = editDiv.find(".oldOutcometypeLimit");
        outcometypeLimit.text(oldOutcometypeLimit.val());
        stopEdit(editDiv);
    });

    $('.editOutcometypeName').on('keyup keydown', function (event) {
        var editDiv = $(this).closest('.editOutcometypeDiv');
        var esc = event.which === 27;
        if (esc) {
            stopEdit(editDiv);
            var oldOutcometypeName = editDiv.find(".oldOutcometypeName");
            $(this).text(oldOutcometypeName.val());
        }
        var enter = event.which === 13;
        if (enter) {
            event.preventDefault();
        }
    }).click(function (e) {
        var isContenteditable = $(this).attr("contenteditable");
        if (isContenteditable === "true") {
            e.preventDefault();
        }
    });

    $('.editOutcometypeLimit').on('keyup keydown', function (event) {
        var editDiv = $(this).closest('.editOutcometypeDiv');
        var esc = event.which === 27;
        if (esc) {
            stopEdit(editDiv);
            var oldOutcometypeLimit = $(this).closest('.editOutcometypeDiv').find(".oldOutcometypeLimit");
            $(this).text(oldOutcometypeLimit.val());
        }
        var enter = event.which === 13;
        if (enter) {
            event.preventDefault();
        }
    }).click(function (e) {
        var isContenteditable = $(this).attr("contenteditable");
        if (isContenteditable === "true") {
            e.preventDefault();
        }
    });


    function stopEdit(element) {
        element.find(".acceptOutcometypeBtn").popover('destroy');
        element.find(".editOutcometypeName").attr('contenteditable', 'false');
        element.find(".editOutcometypeLimit").attr('contenteditable', 'false');
        element.find(".acceptOutcometypeBtn").hide();
        element.find(".cancelOutcometypeBtn").hide();
        element.find(".editOutcometypeLimit").hide();
        element.find(".editOutcometypeBtn").show();
        element.find(".defaultViewOfLimit").show();
    }
});
